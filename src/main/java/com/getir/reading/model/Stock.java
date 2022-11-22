package com.getir.reading.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "stock")
public class Stock implements Serializable {

    @Id
    @Column(name = "code", nullable = false, length = 50)
    private String code;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    public Stock() {
    }

    public Stock(String code, Integer quantity) {
        this.code = code;
        this.quantity = quantity;
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
}
