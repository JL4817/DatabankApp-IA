/**

 A subclass of Person representing a user of the app.
 */

package com.example.cs_ia_app.Models;

public class User extends Person {

    private String occupation;

    /**
     * Default constructor for the user. Initializes all fields to "unknown".
     */
    public User() {
        this.occupation = "unknown";
    }

    /**
     * Constructs a new User object with the given parameters.
     *
     * @param name     the name of the user.
     * @param email    the email of the user.
     * @param userID   the ID of the user.
     * @param occupation the occupation of the user.
     * @param type     the type of user (admin, standard).
     * @param isValid  true if the user is valid, false otherwise.
     */
    public User(String name, String email, String userID, String occupation, String type, boolean isValid) {
        super(name, email, userID, type, isValid);
        this.occupation = occupation;
    }

    /**
     * Returns the occupation of the user.
     *
     * @return the occupation of the user.
     */
    public String getOccupation() {
        return occupation;
    }

    /**
     * Sets the occupation of the user.
     *
     * @param occupation the occupation to set for the user.
     */
    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    /**
     * Returns a string representation of the User object.
     *
     * @return a string representation of the User object.
     */
    @Override
    public String toString() {
        return "User{" +
                "occupation='" + occupation + '\'' +
                '}';
    }

}


