package com.wide.agus.dto;

import com.wide.agus.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private UUID id;
    private String name;
    private String productType;
    private BigDecimal price;
    private int stock;

    public Product mapToModel() {
        Product product = new Product();
        if (this.id != null) {
            product.setId(this.id);
        }
        product.setName(this.name);
        product.setProductType(this.productType);
        product.setPrice(this.price);
        product.setStock(this.stock);

        return product;
    }
}
