package com.donation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.donation.entity.Donation;
import com.donation.entity.Donor;
import com.donation.entity.NotifyUser;

import org.springframework.stereotype.Repository;

@Repository
public interface DonationRepository extends JpaRepository<Donation, String> {
	Donation findByOrderId(String orderId);
}
