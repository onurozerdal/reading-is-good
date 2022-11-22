package com.getir.reading.model.response;

public class OrderLines {
    private String code;
    private Integer quantity;
    private Double amount;

    public OrderLines(String code, Integer quantity, Double amount) {
        this.code = code;
        this.quantity = quantity;
        this.amount = amount;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
