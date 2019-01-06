package com.donation.controller;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.donation.entity.ContactMethod;
import com.donation.entity.DesignationCode;
import com.donation.entity.Suffix;
import com.donation.entity.Title;
import com.donation.service.DesignationCodeService;

@RestController
@RequestMapping("/donation-api/getdata")
public class DataController {


	  public static final Logger LOGGER = LoggerFactory.getLogger(DataController.class);
	  
	  @Autowired
	  private DesignationCodeService designationCodeService;

	
	  /**
	   * Loads all the records from the Designations table
	   */
	  @RequestMapping(value = "/designationcodelist", method = RequestMethod.GET, produces = APPLICATION_JSON)
	  public List<DesignationCode> allDesignationCodes() {
	    return designationCodeService.getDesignationCode();
	  }

	  /**
	   * Loads all the records from the Designations table
	   */
	  @RequestMapping(value = "/title", method = RequestMethod.GET, produces = APPLICATION_JSON)
	  public List<Title> allTitles() {
	    return designationCodeService.getTitle();
	  }
	  
	  /**
	   * Loads all the records from the Designations table
	   */
	  @RequestMapping(value = "/suffix", method = RequestMethod.GET, produces = APPLICATION_JSON)
	  public List<Suffix> allSuffixes() {
	    return designationCodeService.getSuffix();
	  }
	  
	  /**
	   * Loads all the records from the Designations table
	   */
	  @RequestMapping(value = "/contactmethod", method = RequestMethod.GET, produces = APPLICATION_JSON)
	  public List<ContactMethod> allContactMethods() {
	    return designationCodeService.getContactMethod();
	  }
	  
}
