package com.wide.agus.service;

import com.wide.agus.dto.OrderDTO;
import com.wide.agus.dto.OrderRequest;
import com.wide.agus.exception.ProductOrderException;
import org.springframework.data.domain.Page;

public interface OrderService {
    OrderRequest order(OrderRequest orderRequest) throws ProductOrderException;
    Page<OrderDTO> getOrderByUser(int page, int size);
}
