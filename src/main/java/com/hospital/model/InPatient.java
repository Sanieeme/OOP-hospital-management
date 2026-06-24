package com.bank.model;

public class InPatient extends Patient{
    private int consultationCount;
    public static final double CONSULTATION_RATE = 350.00;

    public InPatient(String patientId, String firstName, String lastName, int age, int consultationCount){
        super(patientId, firstName, lastName, age);
        this.consultationCount = consultationCount;

    }
    public int consultationCount(){
        return consultationCount;
    }

    public void setConsultationCount(int consultationCount) {
        if (consultationCount < 0){
            throw new IllegalArgumentException();
        }
        this.consultationCount = consultationCount ;
    }
    @Override
    public double calculateDailyCost(){
        return CONSULTATION_RATE;
    }

    @Override
    public String toString(){
        return super.toString() + consultationCount;
    }
}
