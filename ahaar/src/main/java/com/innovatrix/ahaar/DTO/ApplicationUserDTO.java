package com.innovatrix.ahaar.DTO;

import com.innovatrix.ahaar.model.ApplicationUser;


public class ApplicationUserDTO {
    private String userName;
    private String email;
    private String password;

    /**
     * Constructs an ApplicationUserDTO with the specified user details.
     *
     * @param userName The name of the user
     * @param email The email address of the user
     * @param password The password for the user account
     */
    public ApplicationUserDTO(String userName, String email, String password) {
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    /**
     * Retrieves the username of the application user.
     *
     * @return the username as a String
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets the username for the application user.
     *
     * @param userName the username to be assigned to the user
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Retrieves the email address associated with the user.
     *
     * @return the email address of the user as a String
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address for the application user.
     *
     * @param email the email address to be assigned to the user
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Retrieves the password associated with the user.
     *
     * @return the user's password as a String
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password for the application user.
     *
     * @param password the new password to be assigned to the user
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Converts the current ApplicationUserDTO instance to an ApplicationUser entity.
     *
     * @return a new ApplicationUser object initialized with the DTO's userName, email, and password
     */
    public ApplicationUser toEntity () {
        return new ApplicationUser(this.userName, this.email, this.password);
    }

}
