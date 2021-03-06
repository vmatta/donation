package com.donation.entity;

import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.apache.commons.lang3.StringUtils.isNumeric;

import com.donation.model.Error;
import com.donation.model.FieldError;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Entity
@Table(name = "donor")
@Getter
@EqualsAndHashCode
@ToString
public class Donor implements Validate {

  @Id
  @Column(name = "order_id", unique = true)
  private final String orderId;
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
  @Column(name = "phone")
  private final String phone;
  @Column(name = "fax")
  private final String fax;
  @Column(name = "email")
  private final String email;
  @Column(name = "preferred_contact_method")
  private final String preferredContactMethod;
  @Column(name = "anonymous")
  private final boolean anonymous;

  public Donor(String orderId, String title, String firstName, String lastName, String suffix, String streetAddress1, String streetAddress2, String streetAddress3, String city,
      String state, String zip, String country, String phone, String fax, String email, String preferredContactMethod, boolean anonymous) {
    this.orderId = orderId;
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
    this.phone = phone;
    this.fax = fax;
    this.email = email;
    this.preferredContactMethod = preferredContactMethod;
    this.anonymous = anonymous;
  }

  public Donor() {
    this(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, false);
  }

  @Override
  public FieldError validate() {
    FieldError fieldError = new FieldError();
    if (!isNumeric(this.orderId) || isEmpty(this.orderId)) {
      fieldError.addError(Error.builder().field("orderId").message(String.format("Numeric OrderSequence Id expected but received %s", this.orderId)).build());
    }
//    if (isEmpty(this.email)) {
//      fieldError.addError(Error.builder().field("email").message("Email id is missing").build());
//    }
//    if (isEmpty(this.phone)) {
//      fieldError.addError(Error.builder().field("phone").message("Phone number is missing").build());
//    }
//    if (isEmpty(this.streetAddress1)) {
//      fieldError.addError(Error.builder().field("streetAddress1").message("Street Address 1 is missing").build());
//    }
    return fieldError;
  }
}
