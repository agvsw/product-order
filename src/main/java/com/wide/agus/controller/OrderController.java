package com.wide.agus.controller;

import com.wide.agus.dto.CommonResponse;
import com.wide.agus.dto.OrderDTO;
import com.wide.agus.dto.OrderRequest;
import com.wide.agus.exception.ProductOrderException;
import com.wide.agus.service.OrderService;
import com.wide.agus.util.ResponseConverter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "Order", description = "order")
@RestController
@RequestMapping("/api/v1/order")
public class OrderController {

    @Autowired
    private OrderService orderService;


    @Operation(summary = "Create new order", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping
    public ResponseEntity<CommonResponse<OrderRequest>> saveProduct(
            @RequestBody OrderRequest orderRequest
    ) throws ProductOrderException {
        return ResponseConverter.toResponseEntity(orderService.order(orderRequest));
    }

    @Operation(summary = "get my order", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping()
    public ResponseEntity<CommonResponse<Page<OrderDTO>>> getProduct(
            @RequestParam("page") int page,
            @RequestParam("size") int size
    ) {
        return ResponseConverter.toResponseEntity(orderService.getOrderByUser(page, size));
    }

}
