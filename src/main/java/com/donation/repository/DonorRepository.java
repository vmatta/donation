package com.donation.repository;

import com.donation.entity.Donor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DonorRepository extends JpaRepository<Donor, String> {
	Donor findByOrderId(String orderId);
}
