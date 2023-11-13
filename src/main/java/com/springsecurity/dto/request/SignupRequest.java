package com.springsecurity.dto.request;

public class SignupRequest {
    private String username;
    private String password;
    private String role;
    private String firstname;
    private String lastname;

    public SignupRequest(String username, String password, String role, String firstname, String lastname) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }
}
