package com.donation.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "address")
@Getter
@ToString
@AllArgsConstructor
@Builder

public class Address {
	@Id
	@Column(name = "addressid")
	private final Integer addressid; // hibernate_sequence
	@Column(name = "addressline1")
	private final String addressline1;
	@Column(name = "addressline2")
	private final String addressline2;
	@Column(name = "addressline3")
	private final String addressline3;
	@Column(name = "city")
	private final String city;
	@Column(name = "stateprovince")
	private final String stateprovince;
	@Column(name = "postalcode")
	private final String postalcode;
	@Column(name = "country")
	private final String country;
	
	public Address() {
		this.addressid=null;
		this.addressline1=null;
		this.addressline2=null;
		this.addressline3=null;
		this.city=null;
		this.stateprovince=null;
		this.postalcode=null;
		this.country=null;
	}
	
}
