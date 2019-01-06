package com.donation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.donation.entity.DonationEntry;
import com.donation.repository.DonationEntryRepository;

@Service
public class DonationEntryService {

	@Autowired
	DonationEntryRepository donationEntryRepository;

	public Long getDonationEntrySequence() {
		// TODO Auto-generated method stub
		return donationEntryRepository.getSequence();
	}
//
//	public void savedonationEntry(DonationEntry donationEntry) {
//		// TODO Auto-generated method stub
//		donationEntryRepository.save(donationEntry);
//	}
//	
//	
}
