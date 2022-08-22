package com.example.cs_ia_app.Models;

public class Person {

    private String name;
    private String email;
    private String userID;
    private String userType;

    public Person(){

    }

    public Person(String name, String email, String userID, String type){
        this.name = name;
        this.email = email;
        this.userID = userID;
        this.userType = type;

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

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", userID='" + userID + '\'' +
                ", type='" + userType + '\'' +
                '}';
    }
}
