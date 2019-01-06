package com.donation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.donation.entity.Suffix;


@Repository
public interface SuffixRepository extends JpaRepository<Suffix, Integer>{

	Suffix findByDisplayvalue(String sString);
}
