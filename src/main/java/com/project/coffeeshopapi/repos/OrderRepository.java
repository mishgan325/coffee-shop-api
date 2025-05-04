package com.project.coffeeshopapi.repos;

import com.project.coffeeshopapi.models.Order;
import com.project.coffeeshopapi.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByOrderer(User user);

}
