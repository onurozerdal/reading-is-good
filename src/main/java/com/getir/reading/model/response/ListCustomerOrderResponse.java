package com.getir.reading.model.response;

import java.util.List;

public class ListCustomerOrderResponse {
    private List<OrderResponse> orderResponses;
    private Integer page;
    private Integer pageSize;

    public ListCustomerOrderResponse(List<OrderResponse> orderResponses, Integer page, Integer pageSize) {
        this.orderResponses = orderResponses;
        this.page = page;
        this.pageSize = pageSize;
    }

    public List<OrderResponse> getOrderResponses() {
        return orderResponses;
    }

    public void setOrderResponses(List<OrderResponse> orderResponses) {
        this.orderResponses = orderResponses;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
