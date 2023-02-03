package com.vega.credit.handler;

import com.vega.credit.dao.OfferDAO;
import com.vega.credit.enums.OfferStatus;
import com.vega.credit.model.Offer;
import com.vega.credit.util.DateTimeUtil;
import com.vega.credit.util.ValidateOffer;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.xml.bind.ValidationException;
import java.util.List;

@Component
public class LimitOfferHandler {
    @Autowired
    OfferDAO offerDAO;

    public String createLimitOffer(Offer offer) throws ValidationException {
        ValidateOffer.checkOfferValidity(offer);

        String offerId = String.format("offer_%s", DateTimeUtil.getCurrentTimeStamp());
        offer.setOfferId(offerId);
        offer.setOfferStatus(OfferStatus.PENDING);
        offerDAO.addOffer(offer);
        return offerId;
    }

    public List<Offer> fetchActiveOffers(@NonNull String accountId,
                                         @NonNull String activationDate) throws ValidationException {
        if(!DateTimeUtil.isValidDate(activationDate)) {
            throw new ValidationException("Not a valid date format");
        }
        return offerDAO.getActiveOffers(accountId, activationDate);
    }

    public void updateOfferStatus(@NonNull String offerId, @NonNull OfferStatus newStatus) {
        offerDAO.modifyOfferStatus(offerId, newStatus);
    }
}
