package com.bank.model;

public class OutPatient extends Patient{
    public static final double DAILY_RATE = 1200.00;
    private int wardNumber;


    @Override
    public double calculateDailyCost(){
        return DAILY_RATE;
    }

    @Override
    public String toString(){
        return super.toString() + wardNumber;
    }
}
