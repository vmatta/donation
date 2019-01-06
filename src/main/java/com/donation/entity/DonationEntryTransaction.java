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
@Table(name = "donationentrytransaction")
@Getter
@ToString
@AllArgsConstructor
@Builder
public class DonationEntryTransaction {
	@Id
	@Column(name = "donationentrytransactionid")
	private final Integer donationentrytransactionid; // hibernate_sequence
	@Column(name = "upaytransactionid")
	private final String upaytransactionid;
	@Column(name = "upayresultcode")
	private final String upayresultcode;
	@Column(name = "transactiontype")
	private final Integer transactiontype;
	@Column(name = "recieptinfo")
	private final String recieptinfo;
	@Column(name = "upayfullresult")
	private final Long upayfullresult;
	@Column(name = "donationentryid")
	private final Integer donationentryid;
	@Column(name = "cardtype")
	private final String cardtype;
	
	public DonationEntryTransaction() {
		this.donationentrytransactionid = null;
		this.upaytransactionid =null;
		this.upayresultcode = null;
		this.transactiontype = null;
		this.recieptinfo = null;
		this.upayfullresult = null;
		this.donationentryid = null;
		this.cardtype = null;
	}
}
