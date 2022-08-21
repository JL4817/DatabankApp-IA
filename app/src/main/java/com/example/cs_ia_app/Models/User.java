package com.example.cs_ia_app.Models;

public class User extends Person{

    private String occupation;

    public User(){

    }

    public User(String name, String email, String userID, String occupation, String type) {
        super(name, email, userID, type);
        this.occupation = occupation;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    @Override
    public String toString() {
        return "User{" +
                "occupation='" + occupation + '\'' +
                '}';
    }

}
