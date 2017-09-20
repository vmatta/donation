package com.donation.model;

import java.io.Serializable;

/**
 * Created by Sumit on 9/20/2017.
 */
public interface Identifiable<T extends Serializable> {
    T getId();
}
