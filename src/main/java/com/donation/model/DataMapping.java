package com.donation.model;

import com.donation.model.Error.ErrorBuilder;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@EqualsAndHashCode
@ToString
public class DataMapping {
	
	private final String  OrderID;
	private final Integer DonationEntryID;
	private final Integer DonationEntryTransactionID;
	private final Integer DonationEntryDesignationID;
	private final Integer AddressID;
	private final Integer LetterAddressID;

}
