package com.donation.entity;

import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.apache.commons.lang3.StringUtils.isNumeric;

import com.donation.model.Error;
import com.donation.model.FieldError;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Entity
@Table(name = "notifyuser")
@Getter
@EqualsAndHashCode
@ToString
public class NotifyUser implements Validate {

  @Id
  @GeneratedValue(generator = "system-uuid")
  @GenericGenerator(name = "system-uuid", strategy = "uuid")
  private final String id;
  @Column(name = "order_id")
  private final String orderId;
  @Column(name = "order_item_number")
  private final Integer order_item_number;
  @Column(name = "title")
  private final String title;
  @Column(name = "first_name")
  private final String firstName;
  @Column(name = "last_name")
  private final String lastName;
  @Column(name = "suffix")
  private final String suffix;
  @Column(name = "street_address1")
  private final String streetAddress1;
  @Column(name = "street_address2")
  private final String streetAddress2;
  @Column(name = "street_address3")
  private final String streetAddress3;
  @Column(name = "city")
  private final String city;
  @Column(name = "state")
  private final String state;
  @Column(name = "zip")
  private final String zip;
  @Column(name = "country")
  private final String country;
  @Column(name = "email")
  private String email;

  public NotifyUser(String id, String orderId, Integer order_item_number,String title, String firstName, String lastName, String suffix, String streetAddress1, String streetAddress2, String streetAddress3,
      String city, String state, String zip, String country, String email) {
	this.id=id;
	this.orderId = orderId;
    this.order_item_number = order_item_number;
    this.title = title;
    this.firstName = firstName;
    this.lastName = lastName;
    this.suffix = suffix;
    this.streetAddress1 = streetAddress1;
    this.streetAddress2 = streetAddress2;
    this.streetAddress3 = streetAddress3;
    this.city = city;
    this.state = state;
    this.zip = zip;
    this.country = country;
    this.email = email;
  }

  public NotifyUser(String orderId, Integer order_item_number,String title, String firstName, String lastName, String suffix, String streetAddress1, String streetAddress2, String streetAddress3,
	      String city, String state, String zip, String country, String email) {
		this.id=null;
		this.orderId = orderId;
	    this.order_item_number = order_item_number;
	    this.title = title;
	    this.firstName = firstName;
	    this.lastName = lastName;
	    this.suffix = suffix;
	    this.streetAddress1 = streetAddress1;
	    this.streetAddress2 = streetAddress2;
	    this.streetAddress3 = streetAddress3;
	    this.city = city;
	    this.state = state;
	    this.zip = zip;
	    this.country = country;
	    this.email = email;
	  }
  public NotifyUser() {
	  this(null, null, Integer.MAX_VALUE, null, null,null, null,null, null,null, null,null, null,null,null);
  }

  @Override
  public FieldError validate() {
    FieldError fieldError = new FieldError();
    if (!isNumeric(this.orderId) || isEmpty(this.orderId)) {
      fieldError.addError(Error.builder().field("orderId").message(String.format("Numeric OrderSequence Id expected but received %s", this.orderId)).build());
    }
    return fieldError;
  }
}
