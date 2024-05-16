package com.wide.agus.controller;

import com.wide.agus.dto.CommonResponse;
import com.wide.agus.dto.ProductDTO;
import com.wide.agus.service.ProductService;
import com.wide.agus.util.ResponseConverter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "Product", description = "product")
@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Operation(summary = "get all product", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping()
    public ResponseEntity<CommonResponse<PageImpl<ProductDTO>>> getProduct(
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam(value = "keywords", required = false, defaultValue = "") String keywords,
            @Valid
            @RequestParam(
                    required = false,
                    defaultValue = "NAME",
                    name = "sortProperty"
            ) ProductSortProperty sortProperty,

            @Valid
            @RequestParam(
                    required = false,
                    defaultValue = "DESC",
                    name = "direction"
            ) Sort.Direction direction
    ) {
        return ResponseConverter.toResponseEntity(productService.getAllProduct(page, size, keywords, sortProperty.getPropertyName(), direction));
    }

    @Operation(summary = "Save or update product", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping
    public ResponseEntity<CommonResponse<ProductDTO>> saveProduct(
            @RequestBody ProductDTO productDTO
    ) {
        return ResponseConverter.toResponseEntity(productService.save(productDTO));
    }

    @Operation(summary = "Delete product", security = @SecurityRequirement(name = "bearerAuth"))
    @DeleteMapping()
    public ResponseEntity<CommonResponse<List<UUID>>> deleteContent(@RequestBody List<UUID> contentIds) {
        productService.delete(contentIds);
        return ResponseConverter.toResponseEntity(contentIds);
    }

}

@Getter
enum ProductSortProperty {
    NAME("name"),
    type("productType"),
    PRICE("price"),
    STOCK("stock");

    private final String propertyName;

    ProductSortProperty(String propertyName) {
        this.propertyName = propertyName;
    }

}
