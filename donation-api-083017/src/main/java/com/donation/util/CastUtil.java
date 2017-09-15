package com.donation.util;

import liquibase.exception.DateParseException;

import java.util.Date;
import java.util.Map;

import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * Created by Sumit on 8/20/2017.
 */
public class CastUtil {
    public static Date getDateTime(String dateTimeString) {
        if(isBlank(dateTimeString)) {
            return null;
        }
        return DateUtil.parseISODateTimeStringWithTimeZone(dateTimeString);
    }
}
