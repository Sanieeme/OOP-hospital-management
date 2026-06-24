package com.hospital;

import com.hospital.model.*;
import com.hospital.model.Admission.AdmissionStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the Admission class.
 *
 * Covers: constructor validation, all getters, status lifecycle,
 * stay day updates, totalCost delegation, and toString content.
 */
public class AdmissionTest {

    private InPatient patient;
    private Admission admission;

    @BeforeEach
    void setUp() {
        patient = new InPatient("P-001", "Thandiwe", "Khumalo", 34, 5);
        admission = new Admission(1, "Dr. Naidoo", patient, 4);
    }

    // ─── Constructor ──────────────────────────────────────────────────────────

    @Test
    void constructor_setsAdmissionId() {
        assertEquals(1, admission.admissionId());
    }

    @Test
    void constructor_setsDoctor() {
        assertEquals("Dr. Naidoo", admission.doctor());
    }

    @Test
    void constructor_setsPatient() {
        assertEquals(patient, admission.patient());
    }

    @Test
    void constructor_setsStayDays() {
        assertEquals(4, admission.stayDays());
    }

    @Test
    void constructor_defaultsStatusToAdmitted() {
        assertEquals(AdmissionStatus.ADMITTED, admission.status());
    }

    @Test
    void constructor_throwsForZeroStayDays() {
        assertThrows(IllegalArgumentException.class, () ->
                new Admission(2, "Dr. Pillay", patient, 0)
        );
    }

    @Test
    void constructor_throwsForNegativeStayDays() {
        assertThrows(IllegalArgumentException.class, () ->
                new Admission(2, "Dr. Pillay", patient, -2)
        );
    }

    // ─── Immutable fields ─────────────────────────────────────────────────────

    @Test
    void admissionId_hasNoSetter() {
        assertThrows(NoSuchMethodException.class, () ->
                Admission.class.getMethod("setAdmissionId", int.class)
        );
    }

    @Test
    void doctor_hasNoSetter() {
        assertThrows(NoSuchMethodException.class, () ->
                Admission.class.getMethod("setDoctor", String.class)
        );
    }

    @Test
    void patient_hasNoSetter() {
        assertThrows(NoSuchMethodException.class, () ->
                Admission.class.getMethod("setPatient", Patient.class)
        );
    }

    // ─── updateStatus ─────────────────────────────────────────────────────────

    @Test
    void updateStatus_toDischarged_updatesStatus() {
        admission.updateStatus(AdmissionStatus.DISCHARGED);
        assertEquals(AdmissionStatus.DISCHARGED, admission.status());
    }

    @Test
    void updateStatus_toCancelled_updatesStatus() {
        admission.updateStatus(AdmissionStatus.CANCELLED);
        assertEquals(AdmissionStatus.CANCELLED, admission.status());
    }

    @Test
    void updateStatus_toTransferred_updatesStatus() {
        admission.updateStatus(AdmissionStatus.TRANSFERRED);
        assertEquals(AdmissionStatus.TRANSFERRED, admission.status());
    }

    // ─── updateStayDays ───────────────────────────────────────────────────────

    @Test
    void updateStayDays_updatesValue() {
        admission.updateStayDays(10);
        assertEquals(10, admission.stayDays());
    }

    @Test
    void updateStayDays_throwsForZero() {
        assertThrows(IllegalArgumentException.class, () -> admission.updateStayDays(0));
    }

    @Test
    void updateStayDays_throwsForNegative() {
        assertThrows(IllegalArgumentException.class, () -> admission.updateStayDays(-3));
    }

    // ─── totalCost ────────────────────────────────────────────────────────────

    @Test
    void totalCost_delegatesToPatient() {
        // InPatient.DAILY_RATE = 1200.00, stayDays = 4  →  4800.00
        assertEquals(4800.00, admission.totalCost(), 0.001);
    }

    @Test
    void totalCost_updatesAfterStayDaysChange() {
        admission.updateStayDays(7);
        // 1200.00 * 7 = 8400.00
        assertEquals(8400.00, admission.totalCost(), 0.001);
    }

    @Test
    void totalCost_correctForOutPatient() {
        OutPatient outPatient = new OutPatient("P-002", "Sipho", "Dlamini", 28, 3);
        Admission outAdmission = new Admission(2, "Dr. Mokoena", outPatient, 1);
        // OutPatient: 350.00 * 3 = 1050.00, * 1 day = 1050.00
        assertEquals(1050.00, outAdmission.totalCost(), 0.001);
    }

    @Test
    void totalCost_correctForEmergencyPatient() {
        EmergencyPatient emPatient = new EmergencyPatient("P-003", "Lerato", "Moeketsi", 45, 1);
        Admission emAdmission = new Admission(3, "Dr. Botha", emPatient, 2);
        // EmergencyPatient.DAILY_RATE = 2500.00 * 2 days = 5000.00
        assertEquals(5000.00, emAdmission.totalCost(), 0.001);
    }

    @Test
    void totalCost_correctForMaternityPatientWithNicu() {
        MaternityPatient matPatient = new MaternityPatient("P-004", "Nomvula", "Zulu", 29, true);
        Admission matAdmission = new Admission(4, "Dr. Adams", matPatient, 3);
        // (1800.00 + 1000.00) * 3 = 8400.00
        assertEquals(8400.00, matAdmission.totalCost(), 0.001);
    }

    // ─── toString ─────────────────────────────────────────────────────────────

    @Test
    void toString_containsAdmissionId() {
        assertTrue(admission.toString().contains("1"));
    }

    @Test
    void toString_containsDoctorName() {
        assertTrue(admission.toString().contains("Dr. Naidoo"));
    }

    @Test
    void toString_containsPatientId() {
        assertTrue(admission.toString().contains("P-001"));
    }

    @Test
    void toString_containsStayDays() {
        assertTrue(admission.toString().contains("4"));
    }

    @Test
    void toString_containsStatus() {
        assertTrue(admission.toString().toUpperCase().contains("ADMITTED"));
    }

    @Test
    void toString_containsTotalCost() {
        assertTrue(admission.toString().contains("4800"));
    }
}