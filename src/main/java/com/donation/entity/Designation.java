package com.donation.entity;

import static org.apache.commons.lang3.StringUtils.isEmpty;

import com.donation.model.Error;
import com.donation.model.FieldError;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

/**
 * Created by Vijay on 9/19/2017.
 */
@Entity
@Table(name = "designationlist")
@Getter
@ToString
public class Designation implements Validate {

  @Id
  @GeneratedValue(generator = "system-uuid")
  @GenericGenerator(name = "system-uuid", strategy = "uuid")
  private final String id;
  @Column(name = "order_id")
  private final String orderId;
  @Column(name = "order_item_number")
  private final Integer orderItemNumber;
  @Column(name = "designation_name")
  private final String designationName;
  @Column(name = "designation_amount")
  private final BigDecimal designationAmount;

  public Designation(String id, String orderId, Integer orderItemNumber, String designationName, BigDecimal designationAmount) {
    this.id = id;
    this.orderId = orderId;
    this.orderItemNumber = orderItemNumber;
    this.designationName = designationName;
    this.designationAmount = designationAmount;
  }


  public Designation(String orderId, Integer orderItemNumber, String designationName, BigDecimal designationAmount) {
    this.id = null;
    this.orderId = orderId;
    this.orderItemNumber = orderItemNumber;
    this.designationName = designationName;
    this.designationAmount = designationAmount;
  }

  public Designation() {
    this(null, null, Integer.MAX_VALUE, null, null);
  }

  @Override
  public FieldError validate() {
    FieldError fieldError = new FieldError();
    if (isEmpty(this.orderId)) {
      fieldError.addError(Error.builder().field("orderId").message("OrderSequence Id is empty").build());
    }
    return fieldError;
  }
}
