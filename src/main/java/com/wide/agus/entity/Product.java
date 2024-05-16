package com.wide.agus.entity;

import com.wide.agus.dto.ProductDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "products", schema = "wide_test")
public class Product extends Persistent {
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String productType;
    @Column(nullable = false)
    private BigDecimal price;
    @Column(nullable = false)
    private int stock;

    public ProductDTO mapToDTO() {
        return new ProductDTO(this.getId(), this.name, this.productType, this.price, this.stock);
    }
}
