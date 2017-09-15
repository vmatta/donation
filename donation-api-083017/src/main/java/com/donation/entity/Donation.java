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
@Table(name = "donation")
@Getter
@EqualsAndHashCode
@ToString
public class Donation implements Validate {
    @Id
    @Column(name = "orderId", unique = true)
    private final String orderId;
    @Column(name = "dollarAmount")
    private final Integer dollarAmount;
    @Column(name = "anonymous")
    private final boolean anonymous;
    @Column(name = "inHonorOf")
    private final String inHonorOf;
    @Column(name = "inMemoryOf")
    private final String inMemoryOf;
    @Column(name = "notify")
    private final boolean notify;
    

    public Donation(String orderId, Integer dollarAmount, boolean anonymous, String inHonorOf, String inMemoryOf, boolean notify) {
        this.orderId = orderId;
        this.dollarAmount = dollarAmount;
        this.anonymous = anonymous;
        this.inHonorOf = inHonorOf;
        this.inMemoryOf = inMemoryOf;
        this.notify = notify;
        
    }

    public Donation() {
        this(null, null, false, null, null, false);
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
