package com.donation.service;

import com.donation.entity.OrderSequence;
import com.donation.repository.OrderSequenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Sumit on 9/20/2017.
 */
@Service
public class OrderSequenceService {

  private final OrderSequenceRepository orderSequenceRepository;

  @Autowired
  public OrderSequenceService(OrderSequenceRepository orderSequenceRepository) {
    this.orderSequenceRepository = orderSequenceRepository;
  }

  public String generateOrderId() {
    OrderSequence orderSequence = new OrderSequence();
    OrderSequence save = orderSequenceRepository.save(orderSequence);
    return save.getId();
  }
}
