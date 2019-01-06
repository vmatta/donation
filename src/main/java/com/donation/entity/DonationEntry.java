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
@Table(name = "donationentry")
@Getter
@ToString
@AllArgsConstructor
@Builder
public class DonationEntry {
	@Id
	@Column(name = "donationentryid")
	private final Integer donationentryid; // hibernate_sequence
	@Column(name = "donationamount")
	private final BigDecimal donationamount;
	@Column(name = "donationinfoanonymous")
	private final boolean donationinfoanonymous;
	@Column(name = "inmemoryof")
	private final String inmemoryof;
	@Column(name = "inhonorof")
	private final String inhonorof;
	@Column(name = "sendletter")
	private final boolean sendletter;
	@Column(name = "firstname")
	private final String firstname;
	@Column(name = "lastname")
	private final String lastname;
	@Column(name = "phonenumber")
	private final String phonenumber;
	@Column(name = "faxnumber")
	private final String faxnumber;
	@Column(name = "emailaddress")
	private final String emailaddress;
	@Column(name = "donorinfoanonymous")
	private final boolean donorinfoanonymous;
	@Column(name = "createdate")
	private final Date createdate;
	@Column(name = "exported")
	private final boolean exported;
	@Column(name = "exportdate")
	private final Date exportdate;
	@Column(name = "letteraddressid")
	private final Integer letteraddressid;
	@Column(name = "titleid")
	private final Integer titleid;
	@Column(name = "suffixid")
	private final Integer suffixid;
	@Column(name = "addressid")
	private final Integer addressid;
	@Column(name = "contactmethodid")
	private final Integer contactmethodid;

	public DonationEntry() {
		this.donationentryid = null;
		this.donationamount = null;
		this.donationinfoanonymous = false;
		this.inhonorof = null;
		this.inmemoryof = null;
		this.sendletter = false;
		this.firstname = null;
		this.lastname = null;
		this.phonenumber = null;
		this.faxnumber = null;
		this.emailaddress  = null;
		this.donorinfoanonymous = false;
		this.createdate = null;
		this.exported = false;
		this.exportdate  = null;
		this.letteraddressid = null;
		this.titleid  = null;
		this.suffixid  = null;
		this.addressid  = null;
		this.contactmethodid = null;
	}
}
