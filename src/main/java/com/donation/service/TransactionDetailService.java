package com.donation.service;

import static com.donation.model.TransactionType.CANCEL;
import static org.apache.commons.lang3.StringUtils.isEmpty;

import com.donation.entity.Donor;
import com.donation.entity.TransactionDetail;
import com.donation.exception.DuplicateEntityException;
import com.donation.exception.InvalidEntityException;
import com.donation.exception.InvalidOrderIdException;
import com.donation.model.FieldError;
import com.donation.model.ImageMapper;
import com.donation.model.Mail;
import com.donation.repository.DonorRepository;
import com.donation.repository.OrderSequenceRepository;
import com.donation.repository.TransactionDetailRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

@Service
public class TransactionDetailService {

  public static final Logger LOGGER = LoggerFactory.getLogger(TransactionDetailService.class);
  @Autowired
  private TransactionDetailRepository transactionDetailRepository;
  @Autowired
  private OrderSequenceRepository orderSequenceRepository;
  @Autowired
  private Environment environment;
  @Autowired
  private EmailHtmlSender emailHtmlSender;

  @Autowired
  private DonorRepository donorRepository;

  /**
   * First Validate the entity object . In case, any error found it will throw {@link InvalidEntityException}. Next check is whether there is record with same orderId. If found
   * then this method will throw {@link DuplicateEntityException} exception.
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
    if (orderSequenceRepository.findOne(transactionDetail.getOrderId()) == null) {
      throw new InvalidOrderIdException("Invalid order id");
    }

    TransactionDetail savedTransactionDetail = transactionDetailRepository.save(transactionDetail.markAsSuccess());
    if(savedTransactionDetail.getApproved() == 1){
      Donor donor = donorRepository.findByOrderId(savedTransactionDetail.getOrderId());
      if(donor==null || isEmpty(donor.getEmail())){
        LOGGER.warn("No donor mail id is present with respect to order id {}", savedTransactionDetail.getOrderId());
        return savedTransactionDetail;
      }
      List<ImageMapper> imageFiles = Arrays.asList(ImageMapper.builder()
              .mapperName("imageResourceName")
              .imageFileName("email-banner.jpg")
              .contentType("image/jpeg")
              .build());
      Context context = new Context(Locale.forLanguageTag("en"));
      context.setVariable("title", "UPMC - Transaction Confirmation");
      context.setVariable("firstName", donor.getFirstName());
      context.setVariable("lastName", donor.getLastName());
      context.setVariable("donationAmount", savedTransactionDetail.getPaymentTotal());
      context.setVariable("transactionId", savedTransactionDetail.getTransactionId());
      imageFiles.forEach(imageMapper -> context.setVariable(imageMapper.getMapperName(), imageMapper.getImageFileName()));
      context.setVariable("imageResourceName", "email-banner.jpg");
      Mail mail = Mail.builder().recipient(donor.getEmail()).subject("UPMC - Transaction Confirmation").imageMappers(imageFiles).build();
      emailHtmlSender.send(mail, "email/email-template", context);
    }
    return savedTransactionDetail;
  }

  /**
   * This method accept orderId and mark the transaction as cancel
   */
  public TransactionDetail cancelTransactionDetail(String orderId) {
    LOGGER.info("Storing cancel transaction for order Id : {}", orderId);

    if (StringUtils.isBlank(orderId) || orderSequenceRepository.getOne(orderId) == null) {
      throw new InvalidOrderIdException("Invalid order id");
    }
    if (transactionDetailRepository.exists(orderId)) {
      throw new DuplicateEntityException(String.format("Already record exist against order id %s", orderId));
    }

    TransactionDetail transactionDetail = TransactionDetail.builder().transactionType(CANCEL).approved(-1).orderId(orderId).build();
    return transactionDetailRepository.save(transactionDetail);
  }

  /**
   * Reads all the entry from the {@link TransactionDetail} table and reads the last entry and increment by 1 to get new transaction id. In case, there is no transaction order id
   * present id in the database, first time it will read from application.properties file
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

  public TransactionDetail verifyOrderID(String orderId) {
    return transactionDetailRepository.findOne(orderId);
  }
}
