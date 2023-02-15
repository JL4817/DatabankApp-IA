/**

 The Admin class is a model class that represents an Admin user in the application. It extends the

 Person class and adds an additional field 'canDisableUsers'.
 */

package com.example.cs_ia_app.Models;

public class Admin extends Person {

    private boolean canDisableUsers;

    /**

     Constructs an Admin object with default values.
     */
    public Admin() {
        this.canDisableUsers = false;
    }

    /**

     Constructs an Admin object with specified name, email, user ID, the ability to disable users,
     user type and validity.
     @param name the name of the user
     @param email the email of the user
     @param userID the unique ID of the user
     @param canDisableUsers a boolean indicating whether the user can disable other users
     @param type the type of user
     @param isValid a boolean indicating whether the user is valid or not
     */
    public Admin(String name, String email, String userID, boolean canDisableUsers, String type, boolean isValid) {
        super(name, email, userID, type, isValid);
        this.canDisableUsers = canDisableUsers;
    }

    /**

     Returns whether the admin user can disable other users.
     @return a boolean indicating whether the user can disable other users
     */
    public boolean getDisableUsers() {
        return canDisableUsers;
    }

    /**

     Sets the admin user's ability to disable other users.
     @param canDisableUsers a boolean indicating whether the user can disable other users
     */
    public void setCanDisableUsers(boolean canDisableUsers) {
        this.canDisableUsers = canDisableUsers;
    }

    /**

     Returns a string representation of the Admin object.
     @return a string representation of the Admin object
     */
    @Override
    public String toString() {
        return "Admin{" +
                "canDisableUsers=" + canDisableUsers +
                '}';
    }

}
