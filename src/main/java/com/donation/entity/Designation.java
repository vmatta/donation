package com.donation.entity;

import com.donation.model.Error;
import com.donation.model.FieldError;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;

import static org.apache.commons.lang3.StringUtils.isEmpty;

/**
 * Created by Sumit on 9/19/2017.
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
    @Column(name = "orderId")
    private final String orderId;
    @Column(name = "orderItemNumber")
    private final Integer orderItemNumber;
    @Column(name = "designationName")
    private final String designationName;
    @Column(name = "designationAmount")
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
            fieldError.addError(Error.builder().field("orderId").message("Order Id is empty").build());
        }
        return fieldError;
    }
}
