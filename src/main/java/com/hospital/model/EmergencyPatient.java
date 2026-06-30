package com.hospital.model;

public class EmergencyPatient extends Patient{
    private int triagePriority;
    public static final double DAILY_RATE = 2500.00;

    public EmergencyPatient(String patientId, String firstName, String lastName, int age, int triagePriority){
        super(patientId, firstName, lastName, age);
        if (triagePriority < 1){
            throw new IllegalArgumentException("Triage priority must be greater than 0");
        }
        this.triagePriority = triagePriority;
    }

    public int triagePriority(){
        return triagePriority;
    }

    public void setTriagePriority(int triagePriority) {
        if (triagePriority < 1 || triagePriority > 5){
            throw new IllegalArgumentException();
        }
        this.triagePriority = triagePriority;
    }

    @Override
    public double calculateDailyCost(){
        return DAILY_RATE;
    }

    @Override
    public String toString(){
        return super.toString() + triagePriority;
    }
}
