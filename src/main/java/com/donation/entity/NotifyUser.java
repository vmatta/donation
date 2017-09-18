package com.donation.entity;

import com.donation.model.Error;
import com.donation.model.FieldError;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.apache.commons.lang3.StringUtils.isNumeric;

@Entity
@Table(name = "notifyuser")
@Getter
@EqualsAndHashCode
@ToString
public class NotifyUser implements Validate {
    @Id
    @Column(name = "orderId", unique = true)
    private final String orderId;
    @Column(name = "title")
    private final String title;
    @Column(name = "firstName")
    private final String firstName;
    @Column(name = "lastName")
    private final String lastName;
    @Column(name = "suffix")
    private final String suffix;
    @Column(name = "streetAddress1")
    private final String streetAddress1;
    @Column(name = "streetAddress2")
    private final String streetAddress2;
    @Column(name = "streetAddress3")
    private final String streetAddress3;
    @Column(name = "city")
    private final String city;
    @Column(name = "state")
    private final String state;
    @Column(name = "zip")
    private final String zip;
    @Column(name = "country")
    private final String country;
    
    public NotifyUser(String orderId, String title, String firstName, String lastName, String suffix, String streetAddress1, String streetAddress2, String streetAddress3, String city, String state, String zip, String country) {
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
       
    }

    public NotifyUser() {
        this(null, null, null, null, null, null, null, null, null, null, null, null);
    }

    @Override
    public FieldError validate() {
        FieldError fieldError = new FieldError();
        if (!isNumeric(this.orderId) || isEmpty(this.orderId)) {
            fieldError.addError(Error.builder().field("orderId").message(String.format("Numeric Order Id expected but received %s", this.orderId)).build());
        }
//        if (isEmpty(this.email)) {
//            fieldError.addError(Error.builder().field("email").message("Email id is missing").build());
//        }
//        if (isEmpty(this.phone)) {
//            fieldError.addError(Error.builder().field("phone").message("Phone number is missing").build());
//        }
//        if (isEmpty(this.streetAddress1)) {
//            fieldError.addError(Error.builder().field("streetAddress1").message("Street Address 1 is missing").build());
//        }
        return fieldError;
    }
}
