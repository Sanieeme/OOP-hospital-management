# Hospital Patient Management System
### `oop-004-sysdesign.v1` — Object-Oriented Programming System Design Project

---

## Project Structure

```
hospital-management(oop-004-sysdesign.v1)/
│
├── pom.xml
├── README.md
│
└── src/
    ├── main/
    │   └── java/
    │       └── com/
    │           └── hospital/
    │               │
    │               ├── Main.java
    │               │
    │               ├── model/
    │               │   ├── Patient.java
    │               │   ├── InPatient.java
    │               │   ├── OutPatient.java
    │               │   ├── EmergencyPatient.java
    │               │   ├── MaternityPatient.java
    │               │   └── Admission.java
    │               │
    │               └── service/
    │                   └── Hospital.java
    │
    └── test/
        └── java/
            └── com/
                └── hospital/
                    ├── PatientTest.java
                    ├── PatientSubclassTest.java
                    ├── AdmissionTest.java
                    └── HospitalTest.java
```

---

## Running Your Tests

As you complete each step, run the test suite to track your progress:

```bash
mvn clean compile test
```

> Your goal is to get all tests passing before submission.

---

## Implementation Steps

> It is in your best interest to work through the steps in order as later classes depend on earlier ones.

---

### Step 1 — Implement `Patient` (Abstract Class)

**File:** `src/main/java/com/hospital/model/Patient.java`

`Patient` is the abstract base class for every patient registered at the hospital. It holds all common state and behaviour. The daily care cost calculation is declared abstract because each patient category is billed at a different rate.

#### Fields

| Field | Type | Details |
|---|---|---|
| `patientId` | `String` | Unique patient ID. Must be private and immutable. |
| `firstName` | `String` | Patient's first name. Must be private and immutable. |
| `lastName` | `String` | Patient's last name. Must be private and immutable. |
| `age` | `int` | Patient's age in years. Must be private. |
| `admitted` | `boolean` | Whether the patient is currently admitted. Defaults to `false`. Must be private. |

#### Constructor

Accepts `patientId`, `firstName`, `lastName`, `age`. Sets `admitted` to `false` by default.

#### Methods

| Method | Details |
|---|---|
| `patientId()` | Returns the patient ID. No setter. |
| `firstName()` | Returns the first name. No setter. |
| `lastName()` | Returns the last name. No setter. |
| `age()` | Returns the patient's age. |
| `isAdmitted()` | Returns the admitted flag. |
| `validateAge(int)` | Throws `IllegalArgumentException` if age is negative or greater than 130. |
| `setAdmitted(boolean)` | Updates the admitted flag. |
| `toString()` | Returns a readable summary including `patientId`, `firstName`, `lastName`, and `age`. |
| `calculateDailyCost()` | **Abstract.** Each subclass provides its own rate-based implementation. |

---

### Step 2 — Implement the Four `Patient` Subclasses

Each subclass extends `Patient`, adds one type-specific field, and implements `calculateDailyCost()`.

> **Tip:** validate type-specific fields in their setters and constructors as described below.

---

#### `InPatient`

**File:** `src/main/java/com/hospital/model/InPatient.java`

| | Details |
|---|---|
| Extends | `Patient` |
| Extra field | `wardNumber` (`int`) — private |
| Constructor | Accepts `patientId`, `firstName`, `lastName`, `age`, `wardNumber` |
| `wardNumber()` | Returns the ward number |
| `setWardNumber(int)` | Throws `IllegalArgumentException` if `wardNumber < 1` |
| Daily rate | `R1200.00` — stored as `public static final double DAILY_RATE = 1200.00` |
| `calculateDailyCost()` | Returns `DAILY_RATE` |
| `toString()` | Extends parent summary to include ward number |

---

#### `OutPatient`

**File:** `src/main/java/com/hospital/model/OutPatient.java`

| | Details |
|---|---|
| Extends | `Patient` |
| Extra field | `consultationCount` (`int`) — private |
| Constructor | Accepts `patientId`, `firstName`, `lastName`, `age`, `consultationCount` |
| `consultationCount()` | Returns the number of consultations |
| `setConsultationCount(int)` | Throws `IllegalArgumentException` if `consultationCount < 0` |
| Daily rate | `R350.00` — stored as `public static final double CONSULTATION_RATE = 350.00` |
| `calculateDailyCost()` | Returns `CONSULTATION_RATE * consultationCount` |
| `toString()` | Extends parent summary to include number of consultations |

---

#### `EmergencyPatient`

**File:** `src/main/java/com/hospital/model/EmergencyPatient.java`

| | Details |
|---|---|
| Extends | `Patient` |
| Extra field | `triagePriority` (`int`, 1–5 where 1 is most urgent) — private |
| Constructor | Accepts `patientId`, `firstName`, `lastName`, `age`, `triagePriority` |
| `triagePriority()` | Returns the triage priority |
| `setTriagePriority(int)` | Throws `IllegalArgumentException` if value is outside `1`–`5` |
| Daily rate | `R2500.00` — stored as `public static final double DAILY_RATE = 2500.00` |
| `calculateDailyCost()` | Returns `DAILY_RATE` |
| `toString()` | Extends parent summary to include triage priority |

---

#### `MaternityPatient`

**File:** `src/main/java/com/hospital/model/MaternityPatient.java`

| | Details |
|---|---|
| Extends | `Patient` |
| Extra field | `requiresNicu` (`boolean`) — private and immutable |
| Constructor | Accepts `patientId`, `firstName`, `lastName`, `age`, `requiresNicu` |
| `requiresNicu()` | Returns whether NICU care is required. No setter. |
| Daily rate | `R1800.00` — stored as `public static final double DAILY_RATE = 1800.00` |
| `NICU_SURCHARGE` | `public static final double NICU_SURCHARGE = 1000.00` |
| `calculateDailyCost()` | Returns `DAILY_RATE + NICU_SURCHARGE` if `requiresNicu` is `true`, otherwise `DAILY_RATE` |
| `toString()` | Extends parent summary to include `"NICU"` or `"Standard"` |

---

### Step 3 — Implement `Admission`

**File:** `src/main/java/com/hospital/model/Admission.java`

An `Admission` links a patient to the hospital for a set number of days and tracks its status through a lifecycle.

#### Nested Enum: `AdmissionStatus`

A public nested enum has been defined for you inside `Admission`:

```java
public enum AdmissionStatus { ADMITTED, DISCHARGED, TRANSFERRED, CANCELLED }
```

#### Fields

| Field | Type | Details |
|---|---|---|
| `admissionId` | `int` | Unique ID. Private and immutable. |
| `attendingDoctor` | `String` | Name of the attending doctor. Private and immutable. |
| `patient` | `Patient` | The admitted patient. Private and immutable. |
| `stayDays` | `int` | Number of days for the stay. Private. |
| `status` | `AdmissionStatus` | Defaults to `ADMITTED`. Private. |

#### Constructor

Accepts `admissionId`, `attendingDoctor`, `patient`, `stayDays`. Sets `status` to `AdmissionStatus.ADMITTED`. Throws `IllegalArgumentException` if `stayDays < 1`.

#### Methods

| Method | Details |
|---|---|
| `admissionId()` | Returns `admissionId`. No setter. |
| `doctor()` | Returns `attendingDoctor`. No setter. |
| `patient()` | Returns the `Patient`. No setter. |
| `stayDays()` | Returns stay days. |
| `status()` | Returns current status. |
| `updateStatus(AdmissionStatus)` | Updates the status. |
| `updateStayDays(int)` | Updates stay days. Throws `IllegalArgumentException` if `days < 1`. |
| `totalCost()` | Delegates to `patient.calculateDailyCost() * stayDays` and returns the result. |
| `toString()` | Includes `admissionId`, `attendingDoctor`, patient ID, `stayDays`, `status`, and total cost. |

---

### Step 4 — Implement `Hospital`

**File:** `src/main/java/com/hospital/service/Hospital.java`

`Hospital` is the central service class that manages all registered patients and admissions.

#### Fields

| Field | Type | Details |
|---|---|---|
| `hospitalName` | `String` | The hospital name. Private and immutable. |
| `patients` | `List<Patient>` | All registered patients. Private. |
| `admissions` | `List<Admission>` | All admissions ever created. Private. |
| `admissionCounter` | `int` | Auto-increments per admission. Starts at `0`. Private. |

#### Constructor

Accepts `hospitalName`. Initialises `patients` and `admissions` as empty `ArrayList`s.

#### Methods

| Method | Details |
|---|---|
| `registerPatient(Patient)` | Adds a patient to the system. |
| `patients()` | Returns `Collections.unmodifiableList(patients)`. |
| `availablePatients()` | Returns a new list containing only patients where `isAdmitted()` is `false`. |
| `admit(String, String, int)` | Finds the patient by `patientId` (case-insensitive). Throws `IllegalArgumentException` if not found or already admitted. Creates an `Admission` (`admissionId = ++admissionCounter`), marks the patient as admitted, adds the admission, and returns it. |
| `discharge(int admissionId)` | Finds the admission by ID. Throws `IllegalArgumentException` if not found. Sets status to `DISCHARGED`, marks the patient as not admitted, returns the admission. |
| `cancelAdmission(int admissionId)` | Finds the admission by ID. Throws `IllegalArgumentException` if not found. Sets status to `CANCELLED`, marks the patient as not admitted, returns the admission. |
| `admissions()` | Returns `Collections.unmodifiableList(admissions)`. |
| `name()` | Returns the hospital name. |

---

## UML Class Diagram

Produce a UML class diagram for this project using a digital tool such as **draw.io**. No other tool is allowed.

Your diagram must include all six classes and show the following for each:

- Class name
- All fields with their types and access modifiers (`+` public, `-` private, `#` protected)
- All methods with their return types and parameters
- Relationships between classes — inheritance arrows where one class extends another, and association arrows where one class holds a reference to another

Export your diagram and place it in the **root of your project** using these exact names and formats:

```
uml.pdf
uml.png
uml.jpeg
```

---

## Long Question

Answer this question in `answers.txt`. Do not remove the comments and do not change the format.

### Question — Problem Statement

Based on the system you have implemented, write a brief problem statement that could have motivated its development. Your statement should explain:

1. What problem the system is solving
2. Who would use it
3. What it enables them to do that they could not do before