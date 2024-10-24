package com.rafa.sevenTao.repository;

import com.rafa.sevenTao.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {

}
