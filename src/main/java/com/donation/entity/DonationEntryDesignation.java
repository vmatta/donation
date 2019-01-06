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
@Table(name = "donationentrydesignation")
@Getter
@ToString
@Builder
@AllArgsConstructor

public class DonationEntryDesignation {
	@Id
	@Column(name = "donationentrydesignationid")
	private final Integer donationentrydesignationid; // hibernate_sequence
	@Column(name = "percentage")
	private final float percentage;
	@Column(name = "designationamount")
	private final BigDecimal designationamount;
	@Column(name = "donationentryid")
	private final Integer designationid;
	@Column(name = "designationid")
	private final Integer donationentryid;
	
	public DonationEntryDesignation()
	{
		this.donationentrydesignationid=null;
		this.percentage=100.00f;
		this.designationamount=null;
		this.donationentryid=null;
		this.designationid=null;
		
	}

}
