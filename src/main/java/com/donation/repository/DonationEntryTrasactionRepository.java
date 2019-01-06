package com.donation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.donation.entity.DonationEntryTransaction;
import com.donation.entity.TransactionDetail;

@Repository
public interface DonationEntryTrasactionRepository extends JpaRepository<DonationEntryTransaction, Integer>{

}
