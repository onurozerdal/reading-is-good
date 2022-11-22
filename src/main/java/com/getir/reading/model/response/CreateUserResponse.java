package com.getir.reading.model.response;

public class CreateUserResponse {
    private String email;
    private String role;

    public CreateUserResponse() {

    }

    public CreateUserResponse(String email, String role) {
        this.email = email;
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
