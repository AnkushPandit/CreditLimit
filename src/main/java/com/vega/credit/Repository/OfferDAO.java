package com.vega.credit.Repository;

import com.vega.credit.model.Offer;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class OfferDAO {
    private static final Offers offerList = new Offers();

    public Offers getAllOffers() {
        return offerList;
    }

    public List<Offer> getActiveOffers(String accountId, String activationDate) {
        return offerList.
                getOfferList().
                stream().
                filter(offer ->offer.getAccountId().equals(accountId) &&
                        offer.getOfferActivationTime().equals(activationDate)).
                collect(Collectors.toList());
    }

    public void addOffer(Offer offer) {
        offerList.getOfferList().add(offer);
    }
}
