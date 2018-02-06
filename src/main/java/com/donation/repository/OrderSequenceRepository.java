package com.donation.repository;

import com.donation.entity.OrderSequence;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Sumit on 9/20/2017.
 */
public interface OrderSequenceRepository extends JpaRepository<OrderSequence, String> {

}
