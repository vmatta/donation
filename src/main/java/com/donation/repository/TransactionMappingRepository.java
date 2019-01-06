package com.donation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.donation.entity.TransactionDetail;
import com.donation.entity.TransactionMapping;

@Repository
public interface TransactionMappingRepository extends JpaRepository<TransactionMapping, String>{

}
