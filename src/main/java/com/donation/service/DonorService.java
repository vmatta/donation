package com.donation.service;


import com.donation.entity.Donation;
import com.donation.entity.Donor;
import com.donation.entity.NotifyUser;
import com.donation.exception.DuplicateEntityException;
import com.donation.exception.InvalidEntityException;
import com.donation.model.FieldError;
import com.donation.repository.DonationRepository;
import com.donation.repository.DonorRepository;
import com.donation.repository.NotifyUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Sumit on 8/20/2017.
 */
@Service
public class DonorService {

  public static final Logger LOGGER = LoggerFactory.getLogger(DonorService.class);
  @Autowired
  private DonorRepository donorRepository;
  @Autowired
  private NotifyUserRepository notifyUserRepository;
  @Autowired
  private DonationRepository donationRepository;

  public Donor saveDonorInformation(Donor donor) {
    LOGGER.debug("Request came for storing donor {}", donor);
    FieldError validate = donor.validate();
    if (validate.hasError()) {
      LOGGER.error("Error found while validating donor" + validate.getErrors());
      throw new InvalidEntityException(validate.getErrors());
    }
    if (donorRepository.exists(donor.getOrderId())) {
      throw new DuplicateEntityException(String.format("Entry already exist with order id %s", donor.getOrderId()));
    }
    Donor savedDonor = donorRepository.save(donor);
    LOGGER.info("Successfully stored donor information", savedDonor);
    return savedDonor;
  }

  public NotifyUser saveNotifyUserInformation(NotifyUser notifyuser) {
    // TODO Auto-generated method stub

    LOGGER.info("OrderSequence ID of Request : ", notifyuser.getOrderId());
    LOGGER.debug("Request came for storing notifyuser {}", notifyuser);
    FieldError validate = notifyuser.validate();
    if (validate.hasError()) {
      LOGGER.error("Error found while validating donor" + validate.getErrors());
      throw new InvalidEntityException(validate.getErrors());
    }
    if (notifyUserRepository.exists(notifyuser.getOrderId())) {
      //	notifyUserRepository.delete(notifyuser.getOrderId());

      LOGGER.info("Deleting user notification already existing {}", notifyuser);
//            throw new DuplicateEntityException(String.format("Entry already exist with order id %s", notifyuser.getOrderId()));
    }
    NotifyUser savedNotifyUser = notifyUserRepository.save(notifyuser);
    LOGGER.info("Successfully stored donor information", savedNotifyUser);
    return savedNotifyUser;

  }

  public Donation saveDonation(Donation donation) {
    // TODO Auto-generated method stub

    LOGGER.info("OrderSequence ID of Request : ", donation.getOrderId());
    LOGGER.debug("Request came for storing Donation Information {}", donation);
    FieldError validate = donation.validate();
    if (validate.hasError()) {
      LOGGER.error("Error found while validating donation" + validate.getErrors());
      throw new InvalidEntityException(validate.getErrors());
    }
    if (donationRepository.exists(donation.getOrderId())) {
      //	notifyUserRepository.delete(notifyuser.getOrderId());

      LOGGER.info("Deleting existing donation information {}", donation);
//		            throw new DuplicateEntityException(String.format("Entry already exist with order id %s", notifyuser.getOrderId()));
    }
    Donation savedDonation = donationRepository.save(donation);
    LOGGER.info("Successfully stored donation information", savedDonation);
    return savedDonation;
  }


}
