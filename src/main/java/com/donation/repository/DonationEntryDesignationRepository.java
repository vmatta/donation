package com.donation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.donation.entity.Designation;
import com.donation.entity.DonationEntryDesignation;

@Repository
public interface DonationEntryDesignationRepository  extends JpaRepository<DonationEntryDesignation, Integer> {

}
