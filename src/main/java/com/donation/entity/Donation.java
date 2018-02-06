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
@Table(name = "donation")
@Getter
@EqualsAndHashCode
@ToString
public class Donation implements Validate {

  @Id
  @Column(name = "order_id", unique = true)
  private final String orderId;
  @Column(name = "dollar_amount")
  private final Integer dollarAmount;
  @Column(name = "anonymous")
  private final boolean anonymous;
  @Column(name = "in_honor_of")
  private final String inHonorOf;
  @Column(name = "in_memory_of")
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
      fieldError.addError(Error.builder().field("orderId").message(String.format("Numeric OrderSequence Id expected but received %s", this.orderId)).build());
    }
    return fieldError;
  }
}
