package com.donation.controller;

import com.donation.entity.Donation;
import com.donation.entity.Donor;
import com.donation.entity.NotifyUser;
import com.donation.repository.DonorRepository;
import com.donation.service.DonorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@RestController
@RequestMapping("/donor")
public class DonorController {
    public static final Logger LOGGER = LoggerFactory.getLogger(DonorController.class);
    @Autowired
    private DonorRepository donorRepository;
    @Autowired
    private DonorService donorService;

    /**
     * Loads all the records from the Donor table
     *
     * @return
     */
    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = APPLICATION_JSON)
    public List<Donor> allDonors() {
        return donorRepository.findAll();
    }

    /**
     * Accept order id as request parameter and returns
     * {@link Donor} donor details
     *
     * @param orderId
     * @return
     */
    @RequestMapping(value = "/{orderId}", method = RequestMethod.GET, produces = APPLICATION_JSON)
    public Donor findByOrderId(@PathVariable("orderId") final String orderId) {
        return donorRepository.findByOrderId(orderId);
    }

    /**
     * Store details of a donor in the donor table
     *
     * @param donor
     * @return
     */
    @RequestMapping(value = "/save/donor", method = RequestMethod.POST, consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    public Donor load(@RequestBody final Donor donor) {
        return donorService.saveDonorInformation(donor);
    }
    
    /**
     * Store details of a donor in the donor table
     *
     * @param donor
     * @return
     */
    @RequestMapping(value = "/save/notifyuser", method = RequestMethod.POST, consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    public NotifyUser load(@RequestBody final NotifyUser notifyuser) {
        return donorService.saveNotifyUserInformation(notifyuser);
    }
    
    /**
     * Store details of a Donation in the Donation table
     *
     * @param donation
     * @return
     */
    @RequestMapping(value = "/save/donation", method = RequestMethod.POST, consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    public Donation load(@RequestBody final Donation donation) {
        return donorService.saveDonation(donation);
    }

}


