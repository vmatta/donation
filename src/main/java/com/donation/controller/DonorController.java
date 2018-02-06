package com.donation.controller;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import com.donation.entity.Designation;
import com.donation.entity.Donation;
import com.donation.entity.Donor;
import com.donation.entity.NotifyUser;
import com.donation.repository.DonorRepository;
import com.donation.service.DesignationService;
import com.donation.service.DonorService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/donor")
public class DonorController {

  public static final Logger LOGGER = LoggerFactory.getLogger(DonorController.class);
  @Autowired
  private DonorRepository donorRepository;
  @Autowired
  private DonorService donorService;

  @Autowired
  private DesignationService designationService;

  /**
   * Loads all the records from the Donor table
   */
  @RequestMapping(value = "/all", method = RequestMethod.GET, produces = APPLICATION_JSON)
  public List<Donor> allDonors() {
    return donorRepository.findAll();
  }

  /**
   * Accept order id as request parameter and returns {@link Donor} donor details
   */
  @RequestMapping(value = "/{orderId}", method = RequestMethod.GET, produces = APPLICATION_JSON)
  public Donor findByOrderId(@PathVariable("orderId") final String orderId) {
    return donorRepository.findByOrderId(orderId);
  }

  /**
   * Store details of a donor in the donor table
   */
  @RequestMapping(value = "/save/donor", method = RequestMethod.POST, consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
  public Donor load(@RequestBody final Donor donor) {
    return donorService.saveDonorInformation(donor);
  }

  /**
   * Store details of a donor in the donor table
   */
  @RequestMapping(value = "/save/notifyuser", method = RequestMethod.POST, consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
  public NotifyUser load(@RequestBody final NotifyUser notifyuser) {
    return donorService.saveNotifyUserInformation(notifyuser);
  }

  /**
   * Store details of a Donation in the Donation table
   */
  @RequestMapping(value = "/save/donation", method = RequestMethod.POST, consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
  public Donation load(@RequestBody final Donation donation) {
    return donorService.saveDonation(donation);
  }

  /**
   * Accept list of designations
   */
  @RequestMapping(value = "/save/designation", method = RequestMethod.POST, consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
  public List<Designation> saveDesignations(@RequestBody final List<Designation> designations) {
    return designationService.saveDesignation(designations);
  }
}


