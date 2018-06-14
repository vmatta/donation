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
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
            throw new DuplicateEntityException(String.format("Entry already exist with order id %s", donor.getOrderId()));
        }
        Donor savedDonor = donorRepository.save(donor);
        LOGGER.info("Successfully stored donor information", savedDonor);
        return savedDonor;
    }

    @Transactional
    public List<NotifyUser> saveNotifyUserInformation(List<NotifyUser> notifyusers) {
        List<NotifyUser> savedNotifyUser = new ArrayList<>();
        // TODO Auto-generated method stub
        notifyusers.forEach(notifyUser -> {
            LOGGER.info("OrderSequence ID of Request : ", notifyUser.getOrderId());
            LOGGER.debug("Request came for storing notifyuser {}", notifyUser);
            FieldError validate = notifyUser.validate();
            if (validate.hasError()) {
                LOGGER.error("Error found while validating donor" + validate.getErrors());
                throw new InvalidEntityException(validate.getErrors());
            }
            if (notifyUserRepository.exists(notifyUser.getOrderId())) {
                //	notifyUserRepository.delete(notifyuser.getOrderId());

                LOGGER.info("Deleting user notification already existing {}", notifyUser);
//            throw new DuplicateEntityException(String.format("Entry already exist with order id %s", notifyuser.getOrderId()));
            }
            savedNotifyUser.add(notifyUserRepository.saveAndFlush(notifyUser));

        });
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

    /**
     * method to save multi notify user
     *
     * @param notifyuserList
     * @return
     * @author R.Vijay
     */
    public List<NotifyUser> saveMultiNotifyUserInformation(
            List<NotifyUser> notifyuserList) {
        if (isEmpty(notifyuserList)) {
            return emptyList();
        }
        List<FieldError> fieldErrors = notifyuserList.stream()
                .map(NotifyUser::validate)
                .filter(fieldError -> fieldError.hasError())
                .collect(Collectors.toList());
        if (!isEmpty(fieldErrors)) {
            List<Error> errorList = fieldErrors.stream()
                    .map(fieldError -> fieldError.getErrors())
                    .flatMap(Collection::stream).collect(Collectors.toList());
            LOGGER.error("Error found in the request body: " + errorList);
            throw new InvalidEntityException(errorList);
        }
        Set<String> uniqueOrderIds = notifyuserList.stream()
                .map(NotifyUser::getOrderId).distinct()
                .collect(Collectors.toSet());
        LOGGER.debug(
                "Deleting existing record in case it present in with order ids [{}]",
                uniqueOrderIds);
        // notifyUserRepository.deleteOrders(uniqueOrderIds);
        Set<Map.Entry<String, List<NotifyUser>>> groupedBasedOnOrderId = notifyuserList
                .stream()
                .collect(groupingBy(notifyuser -> notifyuser.getOrderId()))
                .entrySet();
        List<NotifyUser> updatedUsrListWithOrderNumber = new ArrayList<>();
        for (Map.Entry<String, List<NotifyUser>> listEntry : groupedBasedOnOrderId) {
            List<NotifyUser> notifyUsrList = listEntry.getValue();
            // AtomicInteger atomicInteger = new AtomicInteger(1);
            notifyUsrList.stream().forEach(
                    notifyUser -> updatedUsrListWithOrderNumber
                            .add(new NotifyUser(notifyUser.getOrderId(),
                                    notifyUser.getTitle(),
                                    notifyUser.getFirstName(),
                                    notifyUser.getLastName(),

                                    notifyUser.getSuffix(),
                                    notifyUser.getStreetAddress1(),
                                    notifyUser.getStreetAddress2(),
                                    notifyUser.getStreetAddress3(),
                                    notifyUser.getCity(),
                                    notifyUser.getState(),
                                    notifyUser.getZip(),
                                    notifyUser.getCountry(),
                                    notifyUser.getEmail()
                            ))
            );
        }
        List<NotifyUser> finalNotifyUserList = notifyUserRepository
                .save(updatedUsrListWithOrderNumber);
        LOGGER.debug("multiple notify user  Saved successfully : {}",
                finalNotifyUserList);
        return finalNotifyUserList;
    }
}



