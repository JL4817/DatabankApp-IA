package com.example.cs_ia_app.Models;

public class Person {

    private String name;
    private String email;
    private String userID;
    private String userType;
    private boolean isValid;

    public Person() {
        this.name = "unknown";
        this.email = "unknown";
        this.userID = "unknown";
        this.userType = "unknown";
        this.isValid = true;
    }


    public Person(String name, String email, String userID, String userType, boolean isValid) {
        this.name = name;
        this.email = email;
        this.userID = userID;
        this.userType = userType;
        this.isValid = isValid;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public boolean getIsValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", userID='" + userID + '\'' +
                ", userType='" + userType + '\'' +
                ", isValid=" + isValid +
                '}';
    }
}
