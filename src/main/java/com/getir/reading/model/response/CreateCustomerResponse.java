package com.getir.reading.model.response;

public class CreateCustomerResponse {
    private String email;

    public CreateCustomerResponse() {
    }

    public CreateCustomerResponse(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
