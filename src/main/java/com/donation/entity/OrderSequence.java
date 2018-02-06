package com.donation.entity;

import com.donation.model.Identifiable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * Created by Sumit on 9/20/2017.
 */
@Entity
@Table(name = "ORDERSEQ")
public class OrderSequence implements Identifiable<String> {

  @Id
  @GenericGenerator(
      name = "assigned-sequence",
      strategy = "com.donation.configuration.StringSequenceIdentifier",
      parameters = {
          @org.hibernate.annotations.Parameter(
              name = "sequence_name", value = "hibernate_sequence3"),
          @org.hibernate.annotations.Parameter(
              name = "sequence_prefix", value = "1"),
      }
  )
  @GeneratedValue(generator = "assigned-sequence", strategy = GenerationType.SEQUENCE)
  private String id;

  public OrderSequence() {
  }

  public OrderSequence(String id) {
    this.id = id;
  }

  @Override
  public String getId() {
    return id;
  }
}
