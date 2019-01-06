package com.donation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.donation.entity.Designation;
import com.donation.entity.DesignationCode;

@Repository
public interface DesignationCodeRepository extends JpaRepository<DesignationCode, Integer> {

//   List <String> findDesignationCodeById(Integer Id);
	
	List <DesignationCode> findAllByOrderByDesignationnameAsc();
	
	DesignationCode findByDesignationname(String dString);
}
