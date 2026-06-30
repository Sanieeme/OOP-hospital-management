package com.hospital.model;

public class Admission {


    public enum AdmissionStatus {
        ADMITTED, DISCHARGED, TRANSFERRED, CANCELLED
    }
    private int admissionId;
    private String attendingDoctor;
    public Patient patient;
    private int stayDays;
    private AdmissionStatus status;

    public Admission(int admissionId, String attendingDoctor, Patient patient, int stayDays){
        this.admissionId = admissionId;
        this.attendingDoctor = attendingDoctor;
        this.patient = patient;
        if (stayDays < 1){
            throw new IllegalArgumentException("Stay days must be greater than 0");
        }
        this.stayDays = stayDays;
        this.status = AdmissionStatus.ADMITTED;
    }

    public int admissionId() {
        return admissionId;
    }


    public String doctor() {
        return attendingDoctor;
    }
    public Patient patient() {
        return patient;
    }

    public int stayDays() {
        return stayDays;
    }

    public AdmissionStatus status() {
        return status;
    }
    public void updateStatus(AdmissionStatus status){
        this.status = status;
    }
    public void updateStayDays(int days){
        if (days < 1){
            throw new IllegalArgumentException();
        }
        this.stayDays = days;
    }
    public double totalCost(){
        return patient.calculateDailyCost() * stayDays;
    }
    @Override
    public String toString(){
        return admissionId + attendingDoctor + patient.patientId() +
                stayDays + status + totalCost();
    }
}
