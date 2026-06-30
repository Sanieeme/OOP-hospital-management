package com.hospital.model;

public class InPatient extends Patient{
    private int wardNumber;
    public static final double DAILY_RATE = 1200.00;

    public InPatient(String patientId, String firstName, String lastName, int age, int wardNumber){
        super(patientId, firstName, lastName, age);
        if (wardNumber < 1){
            throw new IllegalArgumentException("Ward number must be greater than 0");
        }
        this.wardNumber = wardNumber;

    }
    public int wardNumber(){
        return wardNumber;
    }

    public void setWardNumber(int wardNumber) {
        if (wardNumber < 1){
            throw new IllegalArgumentException();
        }
        this.wardNumber = wardNumber;
    }
    @Override
    public double calculateDailyCost(){
        return DAILY_RATE;
    }

    @Override
    public String toString(){
        return super.toString() + wardNumber;
    }

}
