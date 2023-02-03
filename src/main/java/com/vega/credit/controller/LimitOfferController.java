package com.vega.credit.controller;

import com.vega.credit.enums.OfferStatus;
import com.vega.credit.handler.LimitOfferHandler;
import com.vega.credit.model.Offer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
    public ResponseEntity<Object> createNewLimitOffer(@RequestBody Offer offer) {
        try {
            String offerId = limitOfferHandler.createLimitOffer(offer);
            return ResponseEntity.ok().body(String.format("New offer with id: %s has been created", offerId));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
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

    @PatchMapping(path = "/update", produces = "application/json")
    public ResponseEntity<Object> updateStatusOffer(@RequestParam(value = "offerId") String offerId,
                                  @RequestParam(value = "offerStatus") OfferStatus offerStatus) {
        try {
            limitOfferHandler.updateOfferStatus(offerId, offerStatus);
            return ResponseEntity.ok().body("Offer successfully updated");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
