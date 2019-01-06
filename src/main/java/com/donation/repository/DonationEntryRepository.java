package com.donation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.donation.entity.DonationEntry;

@Repository
public interface DonationEntryRepository extends JpaRepository<DonationEntry, Integer> {

	@Query(value="select hibernate_sequence.nextval from dual", nativeQuery=true)
	Long getSequence();
		
}
