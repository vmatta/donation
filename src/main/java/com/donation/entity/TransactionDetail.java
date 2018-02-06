package com.donation.entity;

import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.apache.commons.lang3.StringUtils.isNumeric;

import com.donation.model.Error;
import com.donation.model.FieldError;
import com.donation.model.TransactionType;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Entity
@Table(name = "transaction_detail")
@Getter
@EqualsAndHashCode
@ToString
@Builder
public class TransactionDetail implements Validate {

  @Id
  @Column(name = "id")
  private final String orderId;
  @Column(name = "app_id")
  private final String appId;
  @Column(name = "app_key")
  private final String appKey;
  @Column(name = "payment_total")
  private final String paymentTotal;
  @Column(name = "has_error")
  private final Boolean hasError;
  @Column(name = "errors")
  private final String errors;
  @Column(name = "approved")
  private final Integer approved;
  @Column(name = "transaction_id")
  private final String transactionId;
  @Column(name = "transaction_date_time")
  private final Date transactionDateTIme;
  @Column(name = "payment_mode")
  private final String paymentMode;
  @Enumerated(EnumType.STRING)
  @Column(name = "transaction_type")
  private TransactionType transactionType;

  public TransactionDetail(String orderId, String appId, String appKey, String paymentTotal, Boolean hasError, String errors, Integer approved, String transactionId,
      Date transactionDateTIme, String paymentMode, TransactionType transactionType) {
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
    this.transactionType = transactionType;
  }

  // Required for hibernate to initialize object
  public TransactionDetail() {
    this(null, null, null, null, null, null, null, null, null, null, null);
  }

  public FieldError validate() {
    FieldError fieldError = new FieldError();
    if (isEmpty(this.orderId) || !isNumeric(this.orderId)) {
      fieldError.addError(Error.builder().field("ORDERID").message(String.format("Numeric OrderSequence Id expected but received %s", this.orderId)).build());
    }
    return fieldError;
  }

  public TransactionDetail markAsSuccess() {
    this.transactionType = TransactionType.SUCCESS;
    return this;
  }

  public TransactionDetail markAsCancel() {
    this.transactionType = TransactionType.CANCEL;
    return this;
  }
}
