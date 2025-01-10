package com.innovatrix.ahaar.model;

import lombok.*;


public class ApplicationUserDTO {
    private String userName;
    private String email;
    private String password;

    public ApplicationUserDTO(String userName, String email, String password) {
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ApplicationUser toEntity () {
        return new ApplicationUser(this.userName, this.email, this.password);
    }

}
