package com.donation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.donation.entity.Title;

@Repository
public interface TitleRepository extends JpaRepository<Title, Integer> {

	Title findByDisplayvalue(String tstring);
}
