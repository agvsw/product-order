package com.wide.agus.service.impl;

import com.wide.agus.dto.ProductDTO;
import com.wide.agus.entity.Product;
import com.wide.agus.repository.ProductRepository;
import com.wide.agus.service.ProductService;
import com.wide.agus.util.JpaSpecificationDSL;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {


    @Autowired
    private ProductRepository productRepository;

    @Override
    public ProductDTO save(ProductDTO productDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null ) {
            Object details = authentication.getDetails();
            Object principals = authentication.getPrincipal();
            log.info("details {}", details);
            log.info("principal {}", principals);
        }


        return productRepository.saveAndFlush(productDTO.mapToModel())
                .mapToDTO();
    }

    @Override
    public void delete(List<UUID> ids) {
        productRepository.deleteAllById(ids);
    }

    @Override
    public PageImpl<ProductDTO> getAllProduct(int page, int size, String keywords, String sortBy, Sort.Direction direction) {
        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.by(direction, sortBy));
        Page<Product> products = productRepository.findAll(specs(keywords), pageRequest);
        List<ProductDTO> productDTOS = products.getContent().stream()
                .map(Product::mapToDTO).toList();
        return new PageImpl<>(productDTOS, products.getPageable(), products.getTotalElements());
    }


    private Specification<Product> specs(String keyword) {
        List<Specification<Product>> specs = new ArrayList<>();
        if (keyword != null && !keyword.isBlank()) {
            List<Specification<Product>> keywordSpecs = new ArrayList<>();
            keywordSpecs.add((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("name"), "%" + keyword + "%"));
            keywordSpecs.add((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("productType"), "%" + keyword + "%"));
            specs.add(JpaSpecificationDSL.or(keywordSpecs));
        }
        return specs.stream().reduce(Specification::and).orElse(null);
    }
}
