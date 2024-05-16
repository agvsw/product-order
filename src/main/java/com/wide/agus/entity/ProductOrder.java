package com.wide.agus.entity;

import com.wide.agus.dto.OrderDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Formula;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "orders",  schema = "wide_test")
public class ProductOrder extends Persistent {
    @ManyToOne(cascade = CascadeType.ALL)
    private User user;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Product product;
    private int qty;
//    @Formula("qty * (SELECT p.price FROM Product p WHERE p.id = product_id)")
    private BigDecimal totalPrice;

    public OrderDTO mapToDto(){
        OrderDTO dto = new OrderDTO();
        dto.setId(this.getId());
        dto.setName(this.product.getName());
        dto.setPrice(this.product.getPrice());
        dto.setQty(this.qty);
        dto.setTotal(this.totalPrice);
        return dto;
    }
}
