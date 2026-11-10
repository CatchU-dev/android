package com.isaacsufyan.catchu.utils

import com.isaacsufyan.catchu.domain.model.RawMeasurements
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Minimal demographics for lab builds when skipping the full questionnaire screen.
 *
 * Backend alignment (pilot): use a dedicated lab `BASE_URL` in the `lab` product flavor when the
 * backend exposes a versioned host. Lab sign-in uses community + patient validation and
 * `create_patient_test_within_group` (no OTP). Coordinate required `raw_measurements` fields for
 * `create_raw_measurements` when demographics are skipped.
 */
object LabSession {

    const val DEFAULT_BLOCKS = 3
    val BLOCK_OPTIONS = listOf(3, 4, 5, 6, 7, 8, 9)

    fun prepareRawMeasurementsForTest(rawMeasurements: RawMeasurements, patientId: Int?) {
        rawMeasurements.testdate =
            SimpleDateFormat("MM-dd-yyyy", Locale.getDefault()).format(Date())
        patientId?.let { rawMeasurements.subjectid = it }
    }
}
