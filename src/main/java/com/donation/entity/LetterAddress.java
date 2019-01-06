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
@Table(name = "letteraddress")
@Getter
@ToString
@AllArgsConstructor
@Builder

public class LetterAddress {
	@Id
	@Column(name = "letteraddressid")
	private final Integer letteraddressid; // hibernate_sequence
	@Column(name = "firstname")
	private final String firstname;
	@Column(name = "lastname")
	private final String lastname;
	@Column(name = "titleid")
	private final Integer titleid;
	@Column(name = "suffixid")
	private final Integer suffixid;
	@Column(name = "addressid")
	private final Integer addressid;
	
	public LetterAddress() {
		this.letteraddressid=null;
		this.firstname=null;
		this.lastname=null;
		this.titleid = null;
		this.suffixid=null;
		this.addressid=null;

	}
	
}
