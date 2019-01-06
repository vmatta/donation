package com.donation.controller;

import com.donation.entity.TransactionDetail;
import com.donation.model.ImageMapper;
import com.donation.model.Mail;
import com.donation.service.EmailHtmlSender;
import com.donation.service.OrderSequenceService;
import com.donation.service.TransactionDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.context.Context;

import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static com.donation.util.CastUtil.getDateTime;

// changed requestmapping from "/"
@RestController
@RequestMapping("/donation-api")
public class TransactionDetailController {

  public static final Logger LOGGER = LoggerFactory.getLogger(TransactionDetailController.class);
  @Autowired
  private TransactionDetailService transactionDetailService;
  @Autowired
  private OrderSequenceService orderSequenceService;

  @ResponseBody
  //@RequestMapping(value = "/customer/results", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON)
  @RequestMapping(value = "/customer/results", method = RequestMethod.POST, produces = MediaType.TEXT_HTML)
  public String getFormData(@RequestParam("OrderID") String orderId,
//      @RequestParam("AppID") String appId,
//      @RequestParam("AppKey") String appKey,
      @RequestParam("PaymentTotal") String paymentTotal,
      @RequestParam("HasErrors") Boolean hasErrors,
      @RequestParam("Errors") String errors,
      @RequestParam("Approved") String approved,
      @RequestParam("UPayTransactionID") String uPayTransactionID,
      @RequestParam("TransactionDateTime") String transactionDateTime
//      @RequestParam("PaymentMode") String paymentMode
      ) {
//    TransactionDetail transactionDetail = TransactionDetail.builder().orderId(orderId)
//        .appId(appId).appKey(appKey).approved(approved).paymentMode(paymentMode).errors(errors)
//        .transactionId(uPayTransactionID).hasError(hasErrors).transactionDateTIme(getDateTime(transactionDateTime)).paymentTotal(paymentTotal)
//        .build();
	  TransactionDetail transactionDetail = TransactionDetail.builder().orderId(orderId)
		        .approved(approved).errors(errors)
		        .transactionId(uPayTransactionID).hasError(hasErrors).transactionDateTIme(getDateTime(transactionDateTime)).paymentTotal(paymentTotal)
		        .build();
    LOGGER.debug("Storing transaction response : {}", transactionDetail);

    TransactionDetail savedTransactionDetail = transactionDetailService.saveTransactionDetail(transactionDetail);
    LOGGER.info("Successfully stored transaction response for OrderSequence Id : {} and Transaction Id : {}", orderId, uPayTransactionID);
    //return savedTransactionDetail;
    
    String myOutput = "uPayTransactionID=" + uPayTransactionID + "<br>";
    myOutput+= "orderId=" + orderId+ "<br>";
    
    return "<html><body> Processing Request ...</body></html>";
  }

  @RequestMapping(value = "/customer/cancel", method = RequestMethod.POST, produces = MediaType.TEXT_HTML)
  public String cancelTransaction(@QueryParam("orderId") String orderId) {
    LOGGER.debug("Request received to mark a transaction as cancel for the order Id {}", orderId);
    
    TransactionDetail cancelTransaction = transactionDetailService.cancelTransactionDetail(orderId);
    
    return "<html><body> Cancelling Request ...</body></html>";
  }
  
  @RequestMapping(value = "/customer/delete", method = RequestMethod.POST, produces = MediaType.TEXT_HTML)
  public void deleteTransaction(@QueryParam("orderId") String orderId) {
    LOGGER.debug("Request received delete a previously cancelled transaction for the order Id {}", orderId);
    
    transactionDetailService.deleteTransactionDetail(orderId);
  }
  
//  @RequestMapping(value = "/customer/cancel", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON)
//  public TransactionDetail cancelTransaction(@QueryParam("orderId") String orderId) {
//    LOGGER.debug("Request received to mark a transaction as cancel for the order Id {}", orderId);
//    return transactionDetailService.cancelTransactionDetail(orderId);
//  }
//  

  @RequestMapping(value = "/getOrderID", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
  public String getNextOrderId() {
    LOGGER.debug("Request received to generate a new order id");
    return orderSequenceService.generateOrderId();
  }


  @RequestMapping(value = "/verifyOrderID/{orderId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
  public TransactionDetail verifyOrderID(@PathVariable("orderId") String orderId) {
    LOGGER.debug("Request received to check whether order is present");
    System.out.println("ORDER ID IN REST : " + orderId);
    return transactionDetailService.verifyOrderID(orderId);
  }
}
