package com.donation.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

/**
 * Created by Sumit on 6/14/2018.
 */
@Builder
@ToString
@Getter
public class Mail {
    String recipient;
    String subject;
    List<ImageMapper> imageMappers;
}
