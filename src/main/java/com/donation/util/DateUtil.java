package com.donation.util;

import com.donation.exception.DateParseException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    	
    	SimpleDateFormat formatter7 = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
    	//String sdate7 = "06/14/2018 23:33:33";
    	Date tdate = formatter7.parse(dateString);
    	
    	System.out.println("dateString : " + dateString + " Pattern " + pattern + "tdate " + tdate);
    	//return DateUtils.parseDateStrictly(dateString, pattern);
    	return tdate;
    } 
//      catch (ParseException e) {
    catch (Exception e) {
      LOGGER.debug("Error during parseDate", e);
      throw new DateParseException(e);
    }
  }
}
