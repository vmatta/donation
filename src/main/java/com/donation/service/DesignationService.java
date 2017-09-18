package com.donation.service;

import com.donation.entity.Designation;
import com.donation.exception.InvalidEntityException;
import com.donation.model.Error;
import com.donation.model.FieldError;
import com.donation.repository.DesignationRepository;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.groupingBy;
import static org.apache.commons.collections.CollectionUtils.isEmpty;


/**
 * Created by Sumit on 9/19/2017.
 */
@Getter
@Service
@Transactional
public class DesignationService {
    public static final Logger LOGGER = LoggerFactory.getLogger(DesignationService.class);
    private final DesignationRepository designationRepository;

    @Autowired
    public DesignationService(DesignationRepository designationRepository) {
        this.designationRepository = designationRepository;
    }

    /**
     * Receives list of designations. First extract designations with unique orderId
     * and delete from database if present. Then it groups designations based on orderId
     * and update the orderIdNumber and stores in the database.
     * @param designations
     * @return {@link List<Designation>}
     */
    public List<Designation> saveDesignation(List<Designation> designations){
        if(isEmpty(designations)){
            return emptyList();
        }
        List<FieldError> fieldErrors = designations.stream().map(Designation::validate).filter(fieldError -> fieldError.hasError()).collect(Collectors.toList());
        if(!isEmpty(fieldErrors)){
            List<Error> errorList = fieldErrors.stream().map(fieldError -> fieldError.getErrors()).flatMap(Collection::stream).collect(Collectors.toList());
            LOGGER.error("Error found in the request: " + errorList);
            throw new InvalidEntityException(errorList);
        }
        Set<String> uniqueOrderIds = designations.stream().map(Designation::getOrderId).distinct().collect(Collectors.toSet());
        LOGGER.debug("Deleting existing record in case it present in with order ids [{}]", uniqueOrderIds);
        designationRepository.deleteOrders(uniqueOrderIds);
        Set<Map.Entry<String, List<Designation>>> groupedBasedOnOrderId = designations.stream().collect(groupingBy(designation -> designation.getOrderId())).entrySet();
        List<Designation> updatedWithOrderItemNumber = new ArrayList<>();
        for(Map.Entry<String, List<Designation>> listEntry : groupedBasedOnOrderId){
            List<Designation> designationList = listEntry.getValue();
            AtomicInteger atomicInteger = new AtomicInteger(1);
            designationList.stream().forEach(designation -> updatedWithOrderItemNumber.add(new Designation(designation.getOrderId(), atomicInteger.getAndIncrement(), designation.getDesignationName(), designation.getDesignationAmount())));
        }
        List<Designation> designationList = designationRepository.save(updatedWithOrderItemNumber);
        LOGGER.debug("designations Saved successfully : {}", designationList);
        return designationList;
    }
}
