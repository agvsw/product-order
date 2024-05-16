package com.wide.agus.service.impl;

import com.wide.agus.dto.OrderDTO;
import com.wide.agus.dto.OrderRequest;
import com.wide.agus.entity.Product;
import com.wide.agus.entity.ProductOrder;
import com.wide.agus.entity.User;
import com.wide.agus.exception.ProductOrderException;
import com.wide.agus.repository.ProductOrderRepository;
import com.wide.agus.repository.ProductRepository;
import com.wide.agus.repository.UserRepository;
import com.wide.agus.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductOrderRepository productOrderRepository;

    @Transactional
    @Override
    public OrderRequest order(OrderRequest orderRequest) throws ProductOrderException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principals = authentication.getPrincipal();
        User user = userRepository.findByUsername((String) principals);
        Product product = productRepository.getReferenceById(orderRequest.getProductId());

        if (product.getStock() <= orderRequest.getQty()) {
            throw new ProductOrderException(4000, "product out of stock");
        }

        ProductOrder order = new ProductOrder();
        order.setUser(user);
        order.setProduct(product);
        order.setQty(orderRequest.getQty());
        order.setTotalPrice(product.getPrice().multiply(new BigDecimal(orderRequest.getQty())));
        product.setStock(product.getStock() - orderRequest.getQty());

        productRepository.saveAndFlush(product);
        productOrderRepository.saveAndFlush(order);

        return orderRequest;
    }

    @Override
    public Page<OrderDTO> getOrderByUser(int page, int size) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principals = authentication.getPrincipal();
        User user = userRepository.findByUsername((String) principals);
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        Page<ProductOrder> productOrders = productOrderRepository.findByUser(user, pageRequest);

        List<OrderDTO> orderDTOS = productOrders.getContent()
                .stream().map(ProductOrder::mapToDto)
                .toList();


        return new PageImpl<>(orderDTOS, productOrders.getPageable(), productOrders.getTotalElements());
    }
}
