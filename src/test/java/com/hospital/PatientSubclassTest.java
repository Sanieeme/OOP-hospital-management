package com.hospital;

import com.hospital.model.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for all four Patient subclasses:
 * InPatient, OutPatient, EmergencyPatient, MaternityPatient.
 *
 * Each subclass section verifies:
 *  - Inheritance from Patient
 *  - Type-specific field getter and setter (or immutability)
 *  - Rate constant value(s)
 *  - calculateDailyCost() correctness
 *  - Setter validation throwing IllegalArgumentException for invalid values
 *  - toString() includes the type-specific detail
 */
public class PatientSubclassTest {

    // =========================================================================
    // InPatient
    // =========================================================================

    @Test
    void inPatient_isInstanceOfPatient() {
        InPatient p = new InPatient("P-001", "Thandiwe", "Khumalo", 34, 5);
        assertInstanceOf(Patient.class, p);
    }

    @Test
    void inPatient_wardNumber_returnsCorrectValue() {
        InPatient p = new InPatient("P-001", "Thandiwe", "Khumalo", 34, 5);
        assertEquals(5, p.wardNumber());
    }

    @Test
    void inPatient_setWardNumber_updatesValue() {
        InPatient p = new InPatient("P-001", "Thandiwe", "Khumalo", 34, 5);
        p.setWardNumber(8);
        assertEquals(8, p.wardNumber());
    }

    @Test
    void inPatient_setWardNumber_throwsForZero() {
        InPatient p = new InPatient("P-001", "Thandiwe", "Khumalo", 34, 5);
        assertThrows(IllegalArgumentException.class, () -> p.setWardNumber(0));
    }

    @Test
    void inPatient_setWardNumber_throwsForNegative() {
        InPatient p = new InPatient("P-001", "Thandiwe", "Khumalo", 34, 5);
        assertThrows(IllegalArgumentException.class, () -> p.setWardNumber(-2));
    }

    @Test
    void inPatient_dailyRate_is1200() {
        assertEquals(1200.00, InPatient.DAILY_RATE, 0.001);
    }

    @Test
    void inPatient_calculateDailyCost_returnsDailyRate() {
        InPatient p = new InPatient("P-001", "Thandiwe", "Khumalo", 34, 5);
        assertEquals(1200.00, p.calculateDailyCost(), 0.001);
    }

    @Test
    void inPatient_toString_containsWardNumber() {
        InPatient p = new InPatient("P-001", "Thandiwe", "Khumalo", 34, 5);
        assertTrue(p.toString().contains("5"));
    }

    // =========================================================================
    // OutPatient
    // =========================================================================

    @Test
    void outPatient_isInstanceOfPatient() {
        OutPatient p = new OutPatient("P-002", "Sipho", "Dlamini", 28, 3);
        assertInstanceOf(Patient.class, p);
    }

    @Test
    void outPatient_consultationCount_returnsCorrectValue() {
        OutPatient p = new OutPatient("P-002", "Sipho", "Dlamini", 28, 3);
        assertEquals(3, p.consultationCount());
    }

    @Test
    void outPatient_setConsultationCount_updatesValue() {
        OutPatient p = new OutPatient("P-002", "Sipho", "Dlamini", 28, 3);
        p.setConsultationCount(5);
        assertEquals(5, p.consultationCount());
    }

    @Test
    void outPatient_setConsultationCount_acceptsZero() {
        OutPatient p = new OutPatient("P-002", "Sipho", "Dlamini", 28, 3);
        assertDoesNotThrow(() -> p.setConsultationCount(0));
    }

    @Test
    void outPatient_setConsultationCount_throwsForNegative() {
        OutPatient p = new OutPatient("P-002", "Sipho", "Dlamini", 28, 3);
        assertThrows(IllegalArgumentException.class, () -> p.setConsultationCount(-1));
    }

    @Test
    void outPatient_consultationRate_is350() {
        assertEquals(350.00, OutPatient.CONSULTATION_RATE, 0.001);
    }

    @Test
    void outPatient_calculateDailyCost_correctForThreeConsultations() {
        OutPatient p = new OutPatient("P-002", "Sipho", "Dlamini", 28, 3);
        assertEquals(1050.00, p.calculateDailyCost(), 0.001);
    }

    @Test
    void outPatient_calculateDailyCost_zeroForZeroConsultations() {
        OutPatient p = new OutPatient("P-002", "Sipho", "Dlamini", 28, 0);
        assertEquals(0.00, p.calculateDailyCost(), 0.001);
    }

    @Test
    void outPatient_toString_containsConsultationCount() {
        OutPatient p = new OutPatient("P-002", "Sipho", "Dlamini", 28, 3);
        assertTrue(p.toString().contains("3"));
    }

    // =========================================================================
    // EmergencyPatient
    // =========================================================================

    @Test
    void emergencyPatient_isInstanceOfPatient() {
        EmergencyPatient p = new EmergencyPatient("P-003", "Lerato", "Moeketsi", 45, 1);
        assertInstanceOf(Patient.class, p);
    }

    @Test
    void emergencyPatient_triagePriority_returnsCorrectValue() {
        EmergencyPatient p = new EmergencyPatient("P-003", "Lerato", "Moeketsi", 45, 1);
        assertEquals(1, p.triagePriority());
    }

    @Test
    void emergencyPatient_setTriagePriority_updatesValue() {
        EmergencyPatient p = new EmergencyPatient("P-003", "Lerato", "Moeketsi", 45, 1);
        p.setTriagePriority(3);
        assertEquals(3, p.triagePriority());
    }

    @Test
    void emergencyPatient_setTriagePriority_acceptsBoundaryOne() {
        EmergencyPatient p = new EmergencyPatient("P-003", "Lerato", "Moeketsi", 45, 1);
        assertDoesNotThrow(() -> p.setTriagePriority(1));
    }

    @Test
    void emergencyPatient_setTriagePriority_acceptsBoundaryFive() {
        EmergencyPatient p = new EmergencyPatient("P-003", "Lerato", "Moeketsi", 45, 1);
        assertDoesNotThrow(() -> p.setTriagePriority(5));
    }

    @Test
    void emergencyPatient_setTriagePriority_throwsForZero() {
        EmergencyPatient p = new EmergencyPatient("P-003", "Lerato", "Moeketsi", 45, 1);
        assertThrows(IllegalArgumentException.class, () -> p.setTriagePriority(0));
    }

    @Test
    void emergencyPatient_setTriagePriority_throwsForSix() {
        EmergencyPatient p = new EmergencyPatient("P-003", "Lerato", "Moeketsi", 45, 1);
        assertThrows(IllegalArgumentException.class, () -> p.setTriagePriority(6));
    }

    @Test
    void emergencyPatient_dailyRate_is2500() {
        assertEquals(2500.00, EmergencyPatient.DAILY_RATE, 0.001);
    }

    @Test
    void emergencyPatient_calculateDailyCost_returnsDailyRate() {
        EmergencyPatient p = new EmergencyPatient("P-003", "Lerato", "Moeketsi", 45, 1);
        assertEquals(2500.00, p.calculateDailyCost(), 0.001);
    }

    @Test
    void emergencyPatient_toString_containsTriagePriority() {
        EmergencyPatient p = new EmergencyPatient("P-003", "Lerato", "Moeketsi", 45, 1);
        assertTrue(p.toString().contains("1"));
    }

    // =========================================================================
    // MaternityPatient
    // =========================================================================

    @Test
    void maternityPatient_isInstanceOfPatient() {
        MaternityPatient p = new MaternityPatient("P-004", "Nomvula", "Zulu", 29, false);
        assertInstanceOf(Patient.class, p);
    }

    @Test
    void maternityPatient_requiresNicu_returnsTrueWhenRequired() {
        MaternityPatient p = new MaternityPatient("P-004", "Nomvula", "Zulu", 29, true);
        assertTrue(p.requiresNicu());
    }

    @Test
    void maternityPatient_requiresNicu_returnsFalseWhenNotRequired() {
        MaternityPatient p = new MaternityPatient("P-004", "Nomvula", "Zulu", 29, false);
        assertFalse(p.requiresNicu());
    }

    @Test
    void maternityPatient_requiresNicu_hasNoSetter() {
        assertThrows(NoSuchMethodException.class, () ->
                MaternityPatient.class.getMethod("setRequiresNicu", boolean.class)
        );
    }

    @Test
    void maternityPatient_dailyRate_is1800() {
        assertEquals(1800.00, MaternityPatient.DAILY_RATE, 0.001);
    }

    @Test
    void maternityPatient_nicuSurcharge_is1000() {
        assertEquals(1000.00, MaternityPatient.NICU_SURCHARGE, 0.001);
    }

    @Test
    void maternityPatient_calculateDailyCost_withoutNicu_returnsDailyRate() {
        MaternityPatient p = new MaternityPatient("P-004", "Nomvula", "Zulu", 29, false);
        assertEquals(1800.00, p.calculateDailyCost(), 0.001);
    }

    @Test
    void maternityPatient_calculateDailyCost_withNicu_includesSurcharge() {
        MaternityPatient p = new MaternityPatient("P-004", "Nomvula", "Zulu", 29, true);
        assertEquals(2800.00, p.calculateDailyCost(), 0.001);
    }

    @Test
    void maternityPatient_toString_containsNicu_whenRequired() {
        MaternityPatient p = new MaternityPatient("P-004", "Nomvula", "Zulu", 29, true);
        assertTrue(p.toString().toUpperCase().contains("NICU"));
    }

    @Test
    void maternityPatient_toString_containsStandard_whenNotRequired() {
        MaternityPatient p = new MaternityPatient("P-004", "Nomvula", "Zulu", 29, false);
        assertTrue(p.toString().toLowerCase().contains("standard"));
    }
}