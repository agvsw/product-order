package com.wide.agus.service;

import com.wide.agus.dto.ProductDTO;
import com.wide.agus.entity.Product;
import org.hibernate.query.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    ProductDTO save(ProductDTO productDTO);
    void delete(List<UUID> id);
    PageImpl<ProductDTO> getAllProduct(int page, int size, String keywords, String sortBy, Sort.Direction direction);
}
