package com.donation.entity;

import com.donation.model.Error;
import com.donation.model.FieldError;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.apache.commons.lang3.StringUtils.isNumeric;

@Entity
@Table(name = "transaction_detail")
@Getter
@EqualsAndHashCode
@ToString
@Builder
public class TransactionDetail implements Validate{
    @Id
    @Column(name = "orderId")
    private final String orderId;
    @Column(name = "appId")
    private final String appId;
    @Column(name = "appKey")
    private final String appKey;
    @Column(name = "paymentTotal")
    private final String paymentTotal;
    @Column(name = "hasError")
    private final Boolean hasError;
    @Column(name = "errors")
    private final String errors;
    @Column(name = "approved")
    private final Integer approved;
    @Column(name = "transactionId")
    private final String transactionId;
    @Column(name = "transactionDateTime")
    private final Date transactionDateTIme;
    @Column(name = "paymentMode")
    private final String paymentMode;

    public TransactionDetail(String orderId, String appId, String appKey, String paymentTotal, Boolean hasError, String errors, Integer approved, String transactionId, Date transactionDateTIme, String paymentMode) {
        this.orderId = orderId;
        this.appId = appId;
        this.appKey = appKey;
        this.paymentTotal = paymentTotal;
        this.hasError = hasError;
        this.errors = errors;
        this.approved = approved;
        this.transactionId = transactionId;
        this.transactionDateTIme = transactionDateTIme;
        this.paymentMode = paymentMode;
    }

    // Required for hibernate to initialize object
    public TransactionDetail(){
        this(null, null, null, null, null, null, null, null, null, null);
    }

    public FieldError validate(){
        FieldError fieldError = new FieldError();
        if(isEmpty(this.orderId) || !isNumeric(this.orderId) ){
            fieldError.addError(Error.builder().field("ORDERID").message(String.format("Numeric Order Id expected but received %s", this.orderId)).build());
        }
        return fieldError;
    }
}
