package com.donation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.donation.entity.ContactMethod;

@Repository
public interface ContactMethodRepository extends JpaRepository<ContactMethod, Integer>{
	

}
