package com.donation.entity;

import com.donation.model.Identifiable;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by Sumit on 9/20/2017.
 */
@Entity
@Table(name = "ORDERSEQ")
public class Order implements Identifiable<String> {
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

    public Order() {
    }

    public Order(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }
}
