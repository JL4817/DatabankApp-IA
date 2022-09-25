package com.example.cs_ia_app.Models;

public class Admin extends Person {

    private boolean canDisableUsers;

    public Admin() {
        this.canDisableUsers = false;
    }

    public Admin(String name, String email, String userID, boolean canDisableUsers, String type, boolean isValid) {
        super(name, email, userID, type, isValid);
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
