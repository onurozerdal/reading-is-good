package com.getir.reading.model.response;

import java.util.List;

public class OrderResponse {
    private long orderNumber;
    private Double totalAmount;
    private List<OrderLines> orderLines;

    public OrderResponse() {
    }

    public OrderResponse(long orderNumber, Double totalAmount, List<OrderLines> orderLines) {
        this.orderNumber = orderNumber;
        this.totalAmount = totalAmount;
        this.orderLines = orderLines;
    }

    public long getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(long orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<OrderLines> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(List<OrderLines> orderLines) {
        this.orderLines = orderLines;
    }
}
