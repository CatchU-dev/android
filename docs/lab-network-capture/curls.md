# Lab test run: captured requests (6 blocks)

Captured from the lab debug build on the emulator (OkHttp BODY log). Host: `https://my.catchu.net`.
No `Authorization` header is sent by any of these calls.

Each call creates or changes data on the server. `create_lab_test` creates a new test, and the
upload writes results for the `test` id in the body. Test id 3477 already exists from this run.

## 1. Create lab session

```bash
curl -X POST 'https://my.catchu.net/api/community/create_lab_test/' \
  -H 'Content-Type: application/json; charset=UTF-8' \
  -d '{"dob":"2026-10-01","first_name":"test","gender":"Male","last_name":"test","num_blocks":6,"study_id":"test","study_site":"test","wave":"test"}'
```

Response (200):

```json
{"status":true,"code":200,"message":"Lab Test Created Successfully","data":{"test_id":3477}}
```

## 2. Upload results (extended `create_raw_measurements`)

Body is in `create_raw_measurements_body.json` (41,168 bytes: 360 trials, 30 practice trials).
Run from the repo root.

```bash
curl -X POST 'https://my.catchu.net/api/test/create_raw_measurements/' \
  -H 'Content-Type: application/json; charset=UTF-8' \
  --data-binary @docs/lab-network-capture/create_raw_measurements_body.json
```

Response (200):

```json
{"status":true,"code":200,"message":"Raw measurements record created","data":{"data_saved_successfully":true}}
```

## 3. Lab report

```bash
curl -X POST 'https://my.catchu.net/api/test/lab_test_report/' \
  -H 'Content-Type: application/x-www-form-urlencoded' \
  -d 'test_id=3477'
```

Response (200):

```json
{"status":true,"code":200,"message":"Process measurments generated"}
```
