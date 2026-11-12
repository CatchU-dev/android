# Lab app: backend requirements

Status: proposal for discussion with the backend team. Nothing here is implemented yet.

## Why separate lab endpoints

The lab build (`IS_LAB_BUILD`, EEG with photodiode squares) is changing the test design:

- The tester picks the number of blocks up front (for example 6 or 9 for EEG, 3 as the default).
- Each block is 60 trials (15 each of V, S, VS and catch). More blocks means more than 45 values per type (6 blocks = 90, 9 blocks = 135).
- A miss is stored as blank instead of 0.
- Each trial carries its true presentation order.

The live app assumes 45 values per type, a zero for a miss, and per-type sorted maps. Changing the shared endpoint risks breaking live uploads, the algorithm and exports. So lab data should go to its own endpoints and its own storage path, and live endpoints stay untouched.

## What the lab app uses today

| Step | Endpoint | Shared with live? |
|---|---|---|
| Create lab session (study site, study id, wave, name, DOB, gender) | `POST api/community/create_lab_test/` | No, lab only |
| Upload test values | `POST api/test/create_raw_measurements/` | Yes, same as live |
| Lab report after upload | `POST api/test/lab_test_report/` (body: `test_id`) | No, lab only |
| Algorithm (`ALGORITHM_API_URL`) | called by live only | Lab skips it |

Live-only calls the lab flow does not use: OTP, terms and conditions, patient prefill, group and batch tests, post-test feedback.

## What has to change

Today lab and live share the same upload endpoint. The only difference is an `isLabTest = true` flag inside the payload. The lab payload is now different from live (per-trial rows, a variable number of trials, `null` for a miss), so the backend has to accept it somewhere. "Live is unchanged" means live requests, data and validation behave exactly as they do now.

### Upload: two options (backend to choose)

**Option A, separate lab endpoint (preferred).** New `POST api/lab/create_raw_measurements/` (or a versioned lab host).
- Live code is not touched, so it cannot break.
- Lab data has its own validation and storage.
- Cost: one new route and handler.

**Option B, extend the existing endpoint.** `api/test/create_raw_measurements/` accepts the new per-trial fields only when `isLabTest` is true.
- No new route.
- The backend adds a lab branch inside live code. Validation must allow more than 45 values and nulls only for lab requests, and live requests that omit the new fields must behave as before.
- Risk: a bug in the branch could affect live.

The app will send the same payload either way. Only the URL differs.

### Existing lab endpoints

1. **`lab_test_report`: keep the endpoint and request (`test_id`)**, but build the report from the new per-trial data. It must not assume 45 values per type.
2. **`create_lab_test`: stays as it is**, plus one new optional field, `num_blocks` (see below). Alternatively send `num_blocks` only with the upload.

## Proposed payload for the lab upload

The payload is the same for Option A or B above. Send one row per trial, in the order shown to the participant. Keep the old per-type maps only if the report still needs them.

```json
{
  "test": 123,
  "lab_config": {
    "num_blocks": 6,
    "trials_per_block": 60,
    "per_condition_per_block": 15,
    "stimulus_ms": 100,
    "response_window_ms": 2000,
    "iti_min_ms": 1000,
    "iti_max_ms": 3000
  },
  "practice_trials": [
    { "attempt": 1, "trial_number": 1, "type": "V", "rt_ms": 412, "responded": true }
  ],
  "trials": [
    {
      "trial_number": 1,
      "block": 1,
      "type": "V",
      "rt_ms": 312,
      "responded": true,
      "outcome": "hit"
    },
    {
      "trial_number": 2,
      "block": 1,
      "type": "C",
      "rt_ms": null,
      "responded": false,
      "outcome": "hit"
    }
  ],
  "raw_measurements": { }
}
```

Field notes:

- `type`: `V`, `S`, `VS` or `C` (catch).
- `rt_ms`: milliseconds from stimulus onset (the response window opens after the 100 ms stimulus, so a real response is always above 100). `null` when there was no response.
- `outcome` per client: V, S, VS with a response is a hit, and with none is a miss. Catch with no response is a hit, and with a response is a false alarm.
- `raw_measurements`: the existing session and demographic fields (`isLabTest`, `testdate`, `subjectid`, and so on) stay as they are today.

## Data handling the backend needs to support

1. **Variable trial count.** The number of trials per type is not fixed at 45. It follows `num_blocks`. No validation should reject keys or rows beyond 45 for lab uploads.
2. **Blank reaction time.** Store missing as null, never 0, so averages don't include it. Exports show an empty cell.
3. **Presentation order.** Keep `trial_number` and `block` per trial so the CSV reflects the real random order.
4. **Practice trials.** Store the 15 practice trials (5 each of V, S and VS) separately from the main data.
5. **Lab report.** Compute from the stored trials. It must work for any `num_blocks`, and treat null correctly.
6. **Live is not affected.** Live requests, validation and the live algorithm behave exactly as today. Lab data goes to its own storage or a clearly flagged lab path, and does not trigger the live algorithm.

## Questions for the backend team

1. Do you prefer a separate lab endpoint (`api/lab/create_raw_measurements/`, our preference so live is not affected) or extending `create_raw_measurements` only when `isLabTest` is true? Either way live requests must behave exactly as today.
2. Does `lab_test_report` have a fixed assumption of 45 per type? Can it work from the new trial rows?
3. Do lab tests need the algorithm at all? Today the lab app skips it.
4. Where should lab data be stored (separate table or a flag), and how will the CSV export for lab look? We want one row per trial with `trial_number`, `block`, `type`, `rt_ms` and `outcome`.
5. Should `num_blocks` go with `create_lab_test` or with the upload? We prefer both, so the export knows the expected trial count.
6. Is there a maximum on payload size? 9 blocks is 540 trials.
7. Do you need a lab-specific `BASE_URL` (the code comment in `LabSession.kt` mentions this), or is a path prefix enough?

## Still open with the client

- Should the wait between trials count from the response or from stimulus onset? The client did not answer.
- Saqib to confirm that catch trials with no response are stored as blank.

## App-side changes (for reference, after backend agrees)

- Tester picks the number of blocks up front (lab build only; default 3).
- Sequence generator: blocks of 60 with 15 per type, with no more than 3 of the same type in a row (also across block boundaries).
- 2 second response window opens after the 100 ms stimulus for every type.
- Random wait between trials, 1 to 3 seconds.
- Fix the repeated-tap bug that makes stimuli fire together.
- Store a blank for no response.
- Upload to the new lab endpoint with the per-trial payload.
- Practice: 5 each of V, S and VS, with the same randomisation rule and no catch trials.
