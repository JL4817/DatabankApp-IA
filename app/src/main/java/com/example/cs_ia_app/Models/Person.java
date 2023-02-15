/**

 The Person class represents a generic person with a name, email, user ID, user type, and validity status.
 */

package com.example.cs_ia_app.Models;

public class Person {

    private String name;
    private String email;
    private String userID;
    private String userType;
    private boolean isValid;

    /**
     * Default constructor initializes all fields to unknown values and sets isValid to true.
     */
    public Person() {
        this.name = "unknown";
        this.email = "unknown";
        this.userID = "unknown";
        this.userType = "unknown";
        this.isValid = true;
    }


    /**
     * Constructor initializes all fields with provided values.
     *
     * @param name the person's name
     * @param email the person's email address
     * @param userID the person's user ID
     * @param userType the person's user type (e.g. admin, student, teacher)
     * @param isValid the person's validity status (true if valid, false if not)
     */
    public Person(String name, String email, String userID, String userType, boolean isValid) {
        this.name = name;
        this.email = email;
        this.userID = userID;
        this.userType = userType;
        this.isValid = isValid;
    }

    /**
     * Returns the person's name.
     *
     * @return the person's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the person's name to the specified value.
     *
     * @param name the new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the person's email address.
     *
     * @return the person's email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the person's email address to the specified value.
     *
     * @param email the new email address
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the person's user ID.
     *
     * @return the person's user ID
     */
    public String getUserID() {
        return userID;
    }

    /**
     * Sets the person's user ID to the specified value.
     *
     * @param userID the new user ID
     */
    public void setUserID(String userID) {
        this.userID = userID;
    }

    /**
     * Returns the person's user type.
     *
     * @return the person's user type
     */
    public String getUserType() {
        return userType;
    }

    /**
     * Sets the person's user type to the specified value.
     *
     * @param userType the new user type
     */
    public void setUserType(String userType) {
        this.userType = userType;
    }

    /**
     * Returns the person's validity status.
     *
     * @return true if the person is valid, false if not
     */
    public boolean getIsValid() {
        return isValid;
    }

    /**
     * Sets the person's validity status to the specified value.
     *
     * @param valid true if the person is valid, false if not
     */
    public void setValid(boolean valid) {
        isValid = valid;
    }

    /**
     * Returns a string representation of the person, including all fields.
     *
     * @return a string representation of the person
     */
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
