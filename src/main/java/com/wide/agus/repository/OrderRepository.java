package com.wide.agus.repository;

import com.wide.agus.entity.ProductOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<ProductOrder, UUID>, JpaSpecificationExecutor<ProductOrder> {
}
