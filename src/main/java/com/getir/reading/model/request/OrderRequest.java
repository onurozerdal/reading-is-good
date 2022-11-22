package com.getir.reading.model.request;

import java.util.List;

public class OrderRequest {

    private List<OrderLineRequest> orderLines;

    public List<OrderLineRequest> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(List<OrderLineRequest> orderLines) {
        this.orderLines = orderLines;
    }
}
