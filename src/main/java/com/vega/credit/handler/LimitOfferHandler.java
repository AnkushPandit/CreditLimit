package com.vega.credit.handler;

import com.vega.credit.Repository.OfferDAO;
import com.vega.credit.model.Offer;
import com.vega.credit.util.DateTimeUtil;
import com.vega.credit.util.ValidateOffer;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;

import javax.xml.bind.ValidationException;
import java.util.List;

public class LimitOfferHandler {
    @Autowired
    OfferDAO offerDAO;

    public void createLimitOffer(Offer offer) throws ValidationException {
        ValidateOffer.checkOfferValidity(offer);
        offerDAO.addOffer(offer);
    }

    public List<Offer> fetchActiveOffers(@NonNull String accountId,
                                         @NonNull String activationDate) throws ValidationException {
        if(!DateTimeUtil.isValidDate(activationDate)) {
            throw new ValidationException("Not a valid date format");
        }
        return offerDAO.getActiveOffers(accountId, activationDate);
    }
}
