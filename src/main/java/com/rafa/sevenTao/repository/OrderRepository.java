package com.rafa.sevenTao.repository;

import com.rafa.sevenTao.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {
}
