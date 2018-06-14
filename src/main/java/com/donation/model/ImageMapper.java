package com.donation.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * Created by Sumit on 6/14/2018.
 */
@Builder
@Getter
@ToString
public class ImageMapper {
    private String mapperName;
    private String imageFileName;
    private String contentType;
}
