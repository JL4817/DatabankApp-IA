package com.example.cs_ia_app.Models;

public class Admin extends Person{

    private boolean canDisableUsers = false;

    public Admin(){

    }

    public Admin(String name, String email, String userID, boolean canDisableUsers, String type) {
        super(name, email, userID, type);
        this.canDisableUsers = canDisableUsers;
    }

    public boolean getDisableUsers() {
        return canDisableUsers;
    }

    public void setCanDisableUsers(boolean canDisableUsers) {
        this.canDisableUsers = canDisableUsers;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "canDisableUsers=" + canDisableUsers +
                '}';
    }

}
