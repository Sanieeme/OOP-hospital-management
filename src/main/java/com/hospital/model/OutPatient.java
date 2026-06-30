package com.hospital.model;

public class OutPatient extends Patient{
    public static final double CONSULTATION_RATE = 350.00;
    private int consultationCount;

    public OutPatient(String patientId, String firstName, String lastName, int age, int consultationCount){
        super(patientId, firstName, lastName, age);
        if (consultationCount < 0){
            throw new IllegalArgumentException("Consultation count must be greater than 0");
        }
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
        return CONSULTATION_RATE  * consultationCount;
    }

    @Override
    public String toString(){
        return super.toString() + consultationCount;
    }
}
