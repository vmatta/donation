package com.donation.controller;

import com.donation.entity.TransactionDetail;
import com.donation.service.OrderService;
import com.donation.service.TransactionDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import static com.donation.util.CastUtil.getDateTime;

@RestController
@RequestMapping("/")
public class TransactionDetailController {
    public static final Logger LOGGER = LoggerFactory.getLogger(TransactionDetailController.class);
    @Autowired
    private TransactionDetailService transactionDetailService;
    @Autowired
    private OrderService orderService;

    @ResponseBody
    @RequestMapping(value = "/customer/results", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON)
    public TransactionDetail getFormData(@RequestParam("ORDERID") String orderId,
                                         @RequestParam("APPID") String appId,
                                         @RequestParam("APPKEY") String appKey,
                                         @RequestParam("PaymentTotal") String paymentTotal,
                                         @RequestParam("HasErrors") Boolean hasErrors,
                                         @RequestParam("Errors") String errors,
                                         @RequestParam("Approved") int approved,
                                         @RequestParam("UPayTransactionID") String uPayTransactionID,
                                         @RequestParam("TransactionDateTime") String transactionDateTime,
                                         @RequestParam("PaymentMode") String paymentMode) {
        TransactionDetail transactionDetail = TransactionDetail.builder().orderId(orderId)
                .appId(appId).appKey(appKey).approved(approved).paymentMode(paymentMode).errors(errors)
                .hasError(hasErrors).transactionDateTIme(getDateTime(transactionDateTime)).paymentTotal(paymentTotal)
                .build();
        LOGGER.debug("Storing transaction response : {}", transactionDetail);

        TransactionDetail savedTransactionDetail = transactionDetailService.saveTransactionDetail(transactionDetail);
        LOGGER.info("Successfully stored transaction response for Order Id : {} and Transaction Id : {}", orderId, uPayTransactionID);
        return savedTransactionDetail;
    }

    @RequestMapping(value = "/customer/cancel", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON)
    public TransactionDetail cancelTransaction(@QueryParam("orderId") String orderId) {
        LOGGER.debug("Request received to mark a transaction as cancel for the order Id {}", orderId);
        return transactionDetailService.cancelTransactionDetail(orderId);
    }


    @RequestMapping(value = "/getOrderID", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
    public String getNextOrderId() {
        LOGGER.debug("Request received to generate a new order id");
        return orderService.generateOrderId();
    }


    @RequestMapping(value = "/verifyOrderID/{orderId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
    public TransactionDetail verifyOrderID(@PathVariable("orderId") String orderId) {
        LOGGER.debug("Request received to check whether order is present");
        return transactionDetailService.verifyOrderID(orderId);
    }
}
