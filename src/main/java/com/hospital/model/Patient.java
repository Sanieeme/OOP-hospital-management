package com.hospital.model;

public abstract class Patient {
    private String patienceId;
    private String firstName;
    private String lastName;
    private int age;
    private boolean admitted;

    public Patient(String patientId, String firstName, String lastName, int age){
        this.patienceId = patientId;
        this.firstName =  firstName;
        this.lastName = lastName;
        if (age < 1){
            throw new IllegalArgumentException("Age must be greater than 0");
        }
        this.age = age;
        this.admitted = false;
    }

    public String patientId(){
        return patienceId;
    }
    public String firstName(){
        return firstName;
    }
    public String lastName(){
        return lastName;
    }
    public int age(){
        return age;
    }
    public boolean isAdmitted(){
        return admitted;
    }
    public void validateAge(int age){
        if (age < 0 || age > 130){
            throw new IllegalArgumentException();
        }
        this.age = age;
    }
    public void setAdmitted(boolean admitted){
        this.admitted = admitted;
    }
    @Override
    public String toString(){
        return patienceId + " " + firstName +
                " " + lastName + " " + age;
    }

    public abstract double calculateDailyCost();
}
