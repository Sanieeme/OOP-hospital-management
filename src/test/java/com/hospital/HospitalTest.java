package com.hospital;

import com.hospital.model.*;
import com.hospital.model.Admission.AdmissionStatus;
import com.hospital.service.Hospital;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the Hospital service class.
 *
 * Covers: patient registry management, availability filtering, admit workflow,
 * discharge workflow, cancelAdmission workflow, and error cases.
 */
public class HospitalTest {

    private Hospital hospital;
    private InPatient inPatient;
    private OutPatient outPatient;
    private EmergencyPatient emergencyPatient;
    private MaternityPatient maternityPatient;

    @BeforeEach
    void setUp() {
        hospital          = new Hospital("Chris Hani Baragwanath Hospital");
        inPatient         = new InPatient("P-001", "Thandiwe", "Khumalo", 34, 5);
        outPatient        = new OutPatient("P-002", "Sipho", "Dlamini", 28, 3);
        emergencyPatient  = new EmergencyPatient("P-003", "Lerato", "Moeketsi", 45, 1);
        maternityPatient  = new MaternityPatient("P-004", "Nomvula", "Zulu", 29, false);
    }

    // ─── name ─────────────────────────────────────────────────────────────────

    @Test
    void name_returnsHospitalName() {
        assertEquals("Chris Hani Baragwanath Hospital", hospital.name());
    }

    // ─── registerPatient / patients ──────────────────────────────────────────

    @Test
    void registerPatient_increasesPatientListSize() {
        hospital.registerPatient(inPatient);
        assertEquals(1, hospital.patients().size());
    }

    @Test
    void registerPatient_multiplePatients_allAppearInList() {
        hospital.registerPatient(inPatient);
        hospital.registerPatient(outPatient);
        hospital.registerPatient(emergencyPatient);
        assertEquals(3, hospital.patients().size());
    }

    @Test
    void patients_returnsUnmodifiableList() {
        hospital.registerPatient(inPatient);
        List<Patient> patients = hospital.patients();
        assertThrows(UnsupportedOperationException.class, () -> patients.add(outPatient));
    }

    @Test
    void patients_isEmptyOnNewHospital() {
        assertTrue(hospital.patients().isEmpty());
    }

    // ─── availablePatients ────────────────────────────────────────────────────

    @Test
    void availablePatients_allPatientsAvailableByDefault() {
        hospital.registerPatient(inPatient);
        hospital.registerPatient(outPatient);
        assertEquals(2, hospital.availablePatients().size());
    }

    @Test
    void availablePatients_excludesAdmittedPatients() {
        hospital.registerPatient(inPatient);
        hospital.registerPatient(outPatient);
        hospital.admit("Dr. Naidoo", "P-001", 4);
        assertEquals(1, hospital.availablePatients().size());
    }

    @Test
    void availablePatients_emptyWhenAllAdmitted() {
        hospital.registerPatient(inPatient);
        hospital.admit("Dr. Naidoo", "P-001", 4);
        assertTrue(hospital.availablePatients().isEmpty());
    }

    // ─── admit ────────────────────────────────────────────────────────────────

    @Test
    void admit_returnsAdmissionWithCorrectDoctor() {
        hospital.registerPatient(inPatient);
        Admission admission = hospital.admit("Dr. Naidoo", "P-001", 4);
        assertEquals("Dr. Naidoo", admission.doctor());
    }

    @Test
    void admit_returnsAdmissionWithCorrectPatient() {
        hospital.registerPatient(inPatient);
        Admission admission = hospital.admit("Dr. Naidoo", "P-001", 4);
        assertEquals("P-001", admission.patient().patientId());
    }

    @Test
    void admit_returnsAdmissionWithCorrectStayDays() {
        hospital.registerPatient(inPatient);
        Admission admission = hospital.admit("Dr. Naidoo", "P-001", 7);
        assertEquals(7, admission.stayDays());
    }

    @Test
    void admit_admissionStatusIsAdmitted() {
        hospital.registerPatient(inPatient);
        Admission admission = hospital.admit("Dr. Naidoo", "P-001", 4);
        assertEquals(AdmissionStatus.ADMITTED, admission.status());
    }

    @Test
    void admit_marksPatientAsAdmitted() {
        hospital.registerPatient(inPatient);
        hospital.admit("Dr. Naidoo", "P-001", 4);
        assertTrue(inPatient.isAdmitted());
    }

    @Test
    void admit_admissionIdsAutoIncrement() {
        hospital.registerPatient(inPatient);
        hospital.registerPatient(outPatient);
        Admission a1 = hospital.admit("Dr. Naidoo", "P-001", 4);
        Admission a2 = hospital.admit("Dr. Pillay", "P-002", 1);
        assertNotEquals(a1.admissionId(), a2.admissionId());
    }

    @Test
    void admit_isCaseInsensitiveForPatientId() {
        hospital.registerPatient(inPatient);
        assertDoesNotThrow(() -> hospital.admit("Dr. Naidoo", "p-001", 4));
    }

    @Test
    void admit_throwsIfPatientNotFound() {
        assertThrows(IllegalArgumentException.class, () ->
                hospital.admit("Dr. Naidoo", "UNKNOWN-999", 4)
        );
    }

    @Test
    void admit_throwsIfPatientAlreadyAdmitted() {
        hospital.registerPatient(inPatient);
        hospital.admit("Dr. Naidoo", "P-001", 4);
        assertThrows(IllegalArgumentException.class, () ->
                hospital.admit("Dr. Pillay", "P-001", 2)
        );
    }

    @Test
    void admit_addsAdmissionToAdmissionsHistory() {
        hospital.registerPatient(inPatient);
        hospital.admit("Dr. Naidoo", "P-001", 4);
        assertEquals(1, hospital.admissions().size());
    }

    // ─── discharge ────────────────────────────────────────────────────────────

    @Test
    void discharge_setsStatusToDischarged() {
        hospital.registerPatient(inPatient);
        Admission admission = hospital.admit("Dr. Naidoo", "P-001", 4);
        hospital.discharge(admission.admissionId());
        assertEquals(AdmissionStatus.DISCHARGED, admission.status());
    }

    @Test
    void discharge_makesPatientAvailableAgain() {
        hospital.registerPatient(inPatient);
        Admission admission = hospital.admit("Dr. Naidoo", "P-001", 4);
        hospital.discharge(admission.admissionId());
        assertFalse(inPatient.isAdmitted());
    }

    @Test
    void discharge_returnsTheAdmission() {
        hospital.registerPatient(inPatient);
        Admission admission = hospital.admit("Dr. Naidoo", "P-001", 4);
        Admission discharged = hospital.discharge(admission.admissionId());
        assertEquals(admission.admissionId(), discharged.admissionId());
    }

    @Test
    void discharge_throwsForUnknownAdmissionId() {
        assertThrows(IllegalArgumentException.class, () ->
                hospital.discharge(999)
        );
    }

    @Test
    void discharge_allowsPatientToBeAdmittedAgain() {
        hospital.registerPatient(inPatient);
        Admission admission1 = hospital.admit("Dr. Naidoo", "P-001", 4);
        hospital.discharge(admission1.admissionId());
        assertDoesNotThrow(() -> hospital.admit("Dr. Pillay", "P-001", 2));
    }

    // ─── cancelAdmission ──────────────────────────────────────────────────────

    @Test
    void cancelAdmission_setsStatusToCancelled() {
        hospital.registerPatient(inPatient);
        Admission admission = hospital.admit("Dr. Naidoo", "P-001", 4);
        hospital.cancelAdmission(admission.admissionId());
        assertEquals(AdmissionStatus.CANCELLED, admission.status());
    }

    @Test
    void cancelAdmission_makesPatientAvailableAgain() {
        hospital.registerPatient(inPatient);
        Admission admission = hospital.admit("Dr. Naidoo", "P-001", 4);
        hospital.cancelAdmission(admission.admissionId());
        assertFalse(inPatient.isAdmitted());
    }

    @Test
    void cancelAdmission_returnsTheAdmission() {
        hospital.registerPatient(inPatient);
        Admission admission = hospital.admit("Dr. Naidoo", "P-001", 4);
        Admission cancelled = hospital.cancelAdmission(admission.admissionId());
        assertEquals(admission.admissionId(), cancelled.admissionId());
    }

    @Test
    void cancelAdmission_throwsForUnknownAdmissionId() {
        assertThrows(IllegalArgumentException.class, () ->
                hospital.cancelAdmission(999)
        );
    }

    // ─── admissions ───────────────────────────────────────────────────────────

    @Test
    void admissions_returnsUnmodifiableList() {
        hospital.registerPatient(inPatient);
        hospital.admit("Dr. Naidoo", "P-001", 4);
        List<Admission> admissions = hospital.admissions();
        assertThrows(UnsupportedOperationException.class, () ->
                admissions.add(new Admission(99, "Dr. Hacker", inPatient, 1))
        );
    }

    @Test
    void admissions_isEmptyOnNewHospital() {
        assertTrue(hospital.admissions().isEmpty());
    }

    @Test
    void admissions_historyPreservedAfterDischarge() {
        hospital.registerPatient(inPatient);
        Admission admission = hospital.admit("Dr. Naidoo", "P-001", 4);
        hospital.discharge(admission.admissionId());
        assertEquals(1, hospital.admissions().size());
    }

    @Test
    void admissions_historyPreservedAfterCancel() {
        hospital.registerPatient(inPatient);
        Admission admission = hospital.admit("Dr. Naidoo", "P-001", 4);
        hospital.cancelAdmission(admission.admissionId());
        assertEquals(1, hospital.admissions().size());
    }

    // ─── Full workflow ────────────────────────────────────────────────────────

    @Test
    void fullWorkflow_admitDischargeAdmitAgain() {
        hospital.registerPatient(inPatient);
        hospital.registerPatient(outPatient);

        Admission admission1 = hospital.admit("Dr. Naidoo", "P-001", 4);
        assertEquals(1, hospital.availablePatients().size());

        hospital.discharge(admission1.admissionId());
        assertEquals(2, hospital.availablePatients().size());

        Admission admission2 = hospital.admit("Dr. Pillay", "P-001", 2);
        assertEquals(AdmissionStatus.ADMITTED, admission2.status());
        assertEquals(2, hospital.admissions().size());
    }

    @Test
    void fullWorkflow_withMaternityNicuPatient() {
        hospital.registerPatient(maternityPatient);
        Admission admission = hospital.admit("Dr. Adams", "P-004", 3);
        // MaternityPatient without NICU: 1800.00 * 3 = 5400.00
        assertEquals(5400.00, admission.totalCost(), 0.001);
    }
}