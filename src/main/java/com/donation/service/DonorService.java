//package com.donation.service;
//
//
//import com.donation.entity.Donation;
//import com.donation.entity.Donor;
//import com.donation.entity.NotifyUser;
//import com.donation.exception.DuplicateEntityException;
//import com.donation.exception.InvalidEntityException;
//import com.donation.model.FieldError;
//import com.donation.repository.DonationRepository;
//import com.donation.repository.DonorRepository;
//import com.donation.repository.NotifyUserRepository;
//
//import java.util.*;
//
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;


package com.donation.service;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.groupingBy;
import static org.apache.commons.collections.CollectionUtils.isEmpty;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.hibernate.validator.internal.util.privilegedactions.GetClassLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.donation.entity.Designation;
import com.donation.entity.Donation;
import com.donation.entity.Donor;
import com.donation.entity.NotifyUser;
import com.donation.exception.DuplicateEntityException;
import com.donation.exception.InvalidEntityException;
import com.donation.model.Error;
import com.donation.model.FieldError;
import com.donation.repository.DonationRepository;
import com.donation.repository.DonorRepository;
import com.donation.repository.NotifyUserRepository;
import org.springframework.transaction.annotation.Transactional;

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
        	donorRepository.delete(donor.getOrderId());
           // throw new DuplicateEntityException(String.format("Entry already exist with order id %s", donor.getOrderId()));
        }
        Donor savedDonor = donorRepository.save(donor);
        LOGGER.info("Successfully stored donor information", savedDonor);
        return savedDonor;
    }

    @Transactional
    public List<NotifyUser> saveNotifyUserInformation(List<NotifyUser> notifyusers) {
        List<NotifyUser> savedNotifyUser = new ArrayList<>();
        // TODO Auto-generated method stub
        if (isEmpty(notifyusers)) {
            return emptyList();
          }
        
        Set<String> uniqueOrderIds = notifyusers.stream().map(NotifyUser::getOrderId).distinct().collect(Collectors.toSet());
        LOGGER.debug("Deleting existing Notifications for order ids [{}]", uniqueOrderIds);
        notifyUserRepository.deleteOrders(uniqueOrderIds);
        
        //LOGGER.info("Deleting user notification already existing {}", notifyusers);
        //  throw new DuplicateEntityException(String.format("Entry already exist with order id %s", notifyuser.getOrderId()));
        
        List<NotifyUser> updatedWithOrderItemNumber = new ArrayList<>();
        
        notifyusers.forEach(notifyUser -> {
            LOGGER.info("OrderSequence ID of Request : ", notifyUser.getOrderId());
            LOGGER.debug("Request came for storing notifyuser {}", notifyUser);
            FieldError validate = notifyUser.validate();
            if (validate.hasError()) {
                LOGGER.error("Error found while validating notifyUser" + validate.getErrors());
                throw new InvalidEntityException(validate.getErrors());
            }
        });
            
            Set<Map.Entry<String, List<NotifyUser>>> groupedBasedOnOrderId = notifyusers.stream().collect(groupingBy(nUsr -> nUsr.getOrderId())).entrySet();
//            List<NotifyUser> updatedWithOrderItemNumber = new ArrayList<>();
            for (Map.Entry<String, List<NotifyUser>> listEntry : groupedBasedOnOrderId) {
              List<NotifyUser> notifyUserList = listEntry.getValue();
              AtomicInteger atomicInteger = new AtomicInteger(1);
              notifyUserList.stream().forEach(nUsr -> updatedWithOrderItemNumber
                  .add(new NotifyUser(nUsr.getOrderId(), atomicInteger.getAndIncrement(), nUsr.getTitle(), nUsr.getFirstName(),
                		              nUsr.getLastName(), nUsr.getSuffix(), nUsr.getStreetAddress1(), nUsr.getStreetAddress2(),
                		              nUsr.getStreetAddress3(), nUsr.getCity(), nUsr.getState(), nUsr.getZip(), 
                		              nUsr.getCountry(), nUsr.getEmail()
                		              )));
            }
            
       
        
        List<NotifyUser> notificationList = notifyUserRepository.save(updatedWithOrderItemNumber);
        LOGGER.debug("notifications Saved successfully : {}", notificationList);
       
        return notificationList;
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



