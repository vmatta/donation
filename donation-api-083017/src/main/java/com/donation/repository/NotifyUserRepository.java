package com.donation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.donation.entity.Donor;
import com.donation.entity.NotifyUser;

import org.springframework.stereotype.Repository;

@Repository
public interface NotifyUserRepository extends JpaRepository<NotifyUser, String> {
	NotifyUser findByOrderId(String orderId);
}
