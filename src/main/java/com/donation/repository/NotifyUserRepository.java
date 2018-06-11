package com.donation.repository;

import com.donation.entity.NotifyUser;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NotifyUserRepository extends JpaRepository<NotifyUser, String> {

  NotifyUser findByOrderId(String orderId);
  
  @Modifying
  @Query("delete from NotifyUser d where d.orderId in :orderIds")
  void deleteOrders(@Param("orderIds") Set<String> orderIds);
}
