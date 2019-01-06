package com.donation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.donation.entity.Donation;
import com.donation.entity.LetterAddress;

@Repository
public interface LetterAddressRepository extends JpaRepository<LetterAddress, Integer> { 
	
	
}
