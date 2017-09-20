package com.donation.repository;

import com.donation.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Sumit on 9/20/2017.
 */
public interface OrderRepository extends JpaRepository<Order, String> {
}
