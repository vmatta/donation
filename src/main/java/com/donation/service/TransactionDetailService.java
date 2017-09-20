package com.donation.service;

import com.donation.entity.TransactionDetail;
import com.donation.exception.DuplicateEntityException;
import com.donation.exception.InvalidEntityException;
import com.donation.exception.SequenceOrderMissMatchException;
import com.donation.model.FieldError;
import com.donation.repository.TransactionDetailRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionDetailService {
    public static final Logger LOGGER = LoggerFactory.getLogger(TransactionDetailService.class);
    @Autowired
    private TransactionDetailRepository transactionDetailRepository;
    @Autowired
    private Environment environment;

    /**
     * First Validate the entity object . In case, any error found it will
     * throw {@link InvalidEntityException}.
     * Next check is whether there is record with same orderId. If found
     * then this method will throw {@link DuplicateEntityException} exception.
     *
     * @param transactionDetail
     * @return
     */
    public TransactionDetail saveTransactionDetail(TransactionDetail transactionDetail) {
        LOGGER.debug("Validating the  transactionDetail: {}", transactionDetail);
        FieldError validate = transactionDetail.validate();
        if (validate.hasError()) {
            LOGGER.debug("Error found inside entity :: {}", validate.getErrors());
            throw new InvalidEntityException(validate.getErrors());
        }
        LOGGER.debug("Going to store transaction details : {}", transactionDetail);
        if (transactionDetailRepository.exists(transactionDetail.getOrderId())) {
            throw new DuplicateEntityException(String.format("Already record exist against order id %s", transactionDetail.getOrderId()));
        }
/*        if(!transactionDetail.getOrderId().equals(generateNewOrderId())){
            throw new SequenceOrderMissMatchException("Order id should be in sequential manner");
        }*/
        return transactionDetailRepository.save(transactionDetail);
    }

    /**
     * Reads all the entry from the {@link TransactionDetail} table
     * and reads the last entry and increment by 1 to get new transaction id.
     * In case, there is no transaction order id present id in the database, first time
     * it will read from application.properties file
     *
     * @return
     */
    public String generateNewOrderId() {
        LOGGER.debug("Retrieving last order id from the database.");
        Optional<List<String>> optional = transactionDetailRepository.getOrderIdsInDescendingOrder();
        if (optional.isPresent()) {
            List<String> stringList = optional.get();
            if (!stringList.isEmpty()) {
                return String.valueOf(Long.valueOf(stringList.get(0)) + 1);
            }
        }
        return environment.getProperty("transaction.initialOrderId");
    }

    public TransactionDetail verifyOrderID(String orderId){
        return transactionDetailRepository.findOne(orderId);
    }
}
