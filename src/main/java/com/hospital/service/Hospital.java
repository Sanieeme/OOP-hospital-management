package com.hospital.service;

import com.hospital.model.Admission;
import com.hospital.model.Patient;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Hospital {
    private String hospitalName;
    private List<Patient> patients;
    private List<Admission> admissions;
    private int admissionCounter;

    public Hospital(String hospitalName){
        this.hospitalName = hospitalName;
        this.patients = new ArrayList<>();
        this.admissions = new ArrayList<>();
        this.admissionCounter = 0;
    }

    public void registerPatient(Patient patient){
        this.patients.add(patient);
    }

    public List<Patient> patients(){
        return Collections.unmodifiableList(patients);
    }

    public List<Patient> availablePatients(){
        List<Patient> available = new ArrayList<>();
        for (Patient patient : patients){
            if(!patient.isAdmitted()){
                available.add(patient);
            }
        }
        return available;
    }
    public Admission admit(String doctorName, String id, int stayDays){
        Admission admission;
        for (Patient patient : patients){
            if(patient.patientId().equalsIgnoreCase(id)){
                if (patient.isAdmitted()) {
                    throw new IllegalArgumentException("Patient already admitted");
                }
                patient.setAdmitted(true);
                admission = new Admission(admissionCounter++, doctorName, patient, stayDays);
                admissions.add(admission);
                return admission;
            }
        }
        throw new IllegalArgumentException("Patient not found");
    }
    public Admission discharge(int admissionId){
        for(Admission admission : admissions){
            if (admission.admissionId() == admissionId){
                admission.updateStatus(Admission.AdmissionStatus.DISCHARGED);
                admission.patient().setAdmitted(false);
                return admission;
            }
        }
        throw new IllegalArgumentException("No such admission exists with id: " + admissionId);
    }
    public Admission cancelAdmission(int admissionId){
        for(Admission admission : admissions){
            if (admission.admissionId() == admissionId){
                admission.patient().setAdmitted(false);
                admission.updateStatus(Admission.AdmissionStatus.CANCELLED);
                return admission;
            }
        }
        throw new IllegalArgumentException("No such admission exists with id: " + admissionId);
    }
    public List<Admission> admissions(){
        return Collections.unmodifiableList(admissions);
    }

    public String name(){
        return hospitalName;
    }
}
