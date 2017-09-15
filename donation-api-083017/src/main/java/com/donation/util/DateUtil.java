package com.donation.util;

import com.donation.exception.DateParseException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.util.Date;

/**
 * Created by Sumit on 8/20/2017.
 */
public class DateUtil {
    public static final Logger LOGGER = LoggerFactory.getLogger(DateUtil.class);

    public static Date parseISODateTimeStringWithTimeZone(String dateTimeString) {
        return parseDate(dateTimeString, DateFormatUtils.ISO_DATETIME_TIME_ZONE_FORMAT.getPattern());
    }

    public static Date parseDate(String dateString, String pattern) {
        if (StringUtils.isBlank(dateString)) {
            return null;
        }
        try {
            return DateUtils.parseDateStrictly(dateString, pattern);
        } catch (ParseException e) {
            LOGGER.debug("Error during parseDate", e);
            throw new DateParseException(e);
        }
    }
}
