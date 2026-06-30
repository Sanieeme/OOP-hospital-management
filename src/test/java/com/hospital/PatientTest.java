package com.hospital;

import com.hospital.model.InPatient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for Patient (tested via the concrete InPatient subclass).
 * These tests verify all shared fields and behaviour defined in the abstract base class.
 */
public class PatientTest {

    private InPatient patient;

    @BeforeEach
    void setUp() {
        patient = new InPatient("P-001", "Thandiwe", "Khumalo", 34, 5);
    }

    // ─── patientId ────────────────────────────────────────────────────────────

    @Test
    void patientId_returnsCorrectValue() {
        assertEquals("P-001", patient.patientId());
    }

    @Test
    void patientId_hasNoSetter() {
        assertThrows(NoSuchMethodException.class, () ->
                patient.getClass().getSuperclass().getMethod("setPatientId", String.class)
        );
    }

    // ─── firstName ────────────────────────────────────────────────────────────

    @Test
    void firstName_returnsCorrectValue() {
        assertEquals("Thandiwe", patient.firstName());
    }

    @Test
    void firstName_hasNoSetter() {
        assertThrows(NoSuchMethodException.class, () ->
                patient.getClass().getSuperclass().getMethod("setFirstName", String.class)
        );
    }

    // ─── lastName ─────────────────────────────────────────────────────────────

    @Test
    void lastName_returnsCorrectValue() {
        assertEquals("Khumalo", patient.lastName());
    }

    @Test
    void lastName_hasNoSetter() {
        assertThrows(NoSuchMethodException.class, () ->
                patient.getClass().getSuperclass().getMethod("setLastName", String.class)
        );
    }

    // ─── age ──────────────────────────────────────────────────────────────────

    @Test
    void age_returnsCorrectValue() {
        assertEquals(34, patient.age());
    }

    // ─── admitted / availability ─────────────────────────────────────────────

    @Test
    void isAdmitted_defaultsFalse() {
        assertFalse(patient.isAdmitted(),
                "A newly registered patient should not be admitted by default");
    }

    @Test
    void setAdmitted_true_marksPatientAdmitted() {
        patient.setAdmitted(true);
        assertTrue(patient.isAdmitted());
    }

    @Test
    void setAdmitted_false_marksPatientNotAdmitted() {
        patient.setAdmitted(true);
        patient.setAdmitted(false);
        assertFalse(patient.isAdmitted());
    }

    // ─── validateAge ──────────────────────────────────────────────────────────

    @Test
    void validateAge_acceptsZero() {
        assertDoesNotThrow(() -> patient.validateAge(0));
    }

    @Test
    void validateAge_accepts130() {
        assertDoesNotThrow(() -> patient.validateAge(130));
    }

    @Test
    void validateAge_throwsForNegativeAge() {
        assertThrows(IllegalArgumentException.class, () -> patient.validateAge(-1));
    }

    @Test
    void validateAge_throwsForAgeOver130() {
        assertThrows(IllegalArgumentException.class, () -> patient.validateAge(131));
    }

    // ─── toString ─────────────────────────────────────────────────────────────

    @Test
    void toString_containsPatientId() {
        assertTrue(patient.toString().contains("P-001"));
    }

    @Test
    void toString_containsFirstName() {
        assertTrue(patient.toString().contains("Thandiwe"));
    }

    @Test
    void toString_containsLastName() {
        assertTrue(patient.toString().contains("Khumalo"));
    }

    @Test
    void toString_containsAge() {
        assertTrue(patient.toString().contains("34"));
    }
}