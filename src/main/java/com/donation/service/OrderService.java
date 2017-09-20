package com.donation.service;

import com.donation.entity.Order;
import com.donation.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Sumit on 9/20/2017.
 */
@Service
public class OrderService {
    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public String generateOrderId(){
        Order order = new Order();
        Order save = orderRepository.save(order);
        return save.getId();
    }
}
