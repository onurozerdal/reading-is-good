package com.getir.reading.model.response;

public class SaveBookResponse {
    private String code;
    private Double price;

    public SaveBookResponse() {
    }

    public SaveBookResponse(String code, Double price) {
        this.code = code;
        this.price = price;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
