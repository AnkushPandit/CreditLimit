package com.vega.credit.controller;

import com.vega.credit.handler.LimitOfferHandler;
import com.vega.credit.model.Offer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/offer")
@Service
public class LimitOfferController {
    @Autowired
    LimitOfferHandler limitOfferHandler;

    @PostMapping(path = "/create",
            consumes = "application/json",
            produces = "application/json")
    public void createNewLimitOffer(@RequestBody Offer offer) {
        try {
            limitOfferHandler.createLimitOffer(offer);
        } catch (Exception e) {

        }
    }

    @GetMapping(path = "/get", produces = "application/json")
    public ResponseEntity<Object>
    getActiveLimitOffers(@RequestParam(value = "accountId") String accountId,
                         @RequestParam(value = "activationDate", required = false) String activationDate) {
        try{
            List<Offer> activeLimitOffers =
                    limitOfferHandler.fetchActiveOffers(accountId, activationDate);
            return ResponseEntity.ok().body(activeLimitOffers);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

//    @PutMapping(path = "/update")
//    public void updateStatusOffer(){}
}
