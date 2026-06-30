package com.hospital.model;

public class MaternityPatient extends Patient{
    private boolean requiresNicu;
    public static final double NICU_SURCHARGE = 1000.00;
    public static final double DAILY_RATE = 1800.00;

    public MaternityPatient(String patientId, String firstName, String lastName, int age, boolean requiresNicu){
        super(patientId, firstName, lastName, age);
        this.requiresNicu = requiresNicu;
    }

    public boolean requiresNicu(){
        return requiresNicu;
    }


    @Override
    public double calculateDailyCost(){
        if(!requiresNicu()){
            return DAILY_RATE;
        }
        return DAILY_RATE + NICU_SURCHARGE;
    }

    @Override
    public String toString() {
        if (requiresNicu()) {
            return super.toString() + "NICU";
        }
        return super.toString() + "standard";
    }
}
