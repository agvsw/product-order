package com.wide.agus.repository;

import com.wide.agus.entity.ProductOrder;
import com.wide.agus.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductOrderRepository extends JpaRepository<ProductOrder, UUID> {
    Page<ProductOrder> findByUser(User user, Pageable pageable);
}
