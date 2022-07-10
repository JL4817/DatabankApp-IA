package com.example.cs_ia_app.Models;

public class Admin extends Person{

    private boolean canDisableUsers = false;

    public Admin(String name, String email, String userID, boolean canDisableUsers) {
        super(name, email, userID);
        this.canDisableUsers = canDisableUsers;
    }

    public boolean isCanDisableUsers() {
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
