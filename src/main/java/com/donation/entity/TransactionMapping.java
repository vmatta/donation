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
@Table(name = "transactionmapping")
@Getter
@ToString
@AllArgsConstructor
//@NoArgsConstructor
@Builder
public class TransactionMapping {
	@Id
	@Column(name = "order_id")
	private final String order_id;
	
	@Column(name = "donationentryid")
	private final Integer donationentryid;
	
	public TransactionMapping() {
		this.order_id=null;
		this.donationentryid=null;
		
	}
}
