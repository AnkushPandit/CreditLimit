package com.vega.credit.util;

import com.vega.credit.Repository.AccountDAO;
import com.vega.credit.model.Account;
import com.vega.credit.model.Offer;
import lombok.experimental.UtilityClass;
import org.springframework.beans.factory.annotation.Autowired;

import javax.xml.bind.ValidationException;
import java.util.List;
import java.util.Optional;

import static com.vega.credit.enums.LimitType.ACCOUNT_LIMIT;

@UtilityClass
public class ValidateOffer {

    @Autowired
    AccountDAO accountDAO;

    public void checkOfferValidity(Offer offer) throws ValidationException {
        String accountId = offer.getAccountId();
        if(!Optional.ofNullable(accountId).isPresent()) {
            throw new ValidationException("Offer must have an associated account Id");
        }

        List<Account> accountList = accountDAO.getAccount(accountId);
        if(accountList.size() != 1) {
            throw new ValidationException("No valid account is present for this account Id");
        }
        Account account = accountList.get(0);

        if(ACCOUNT_LIMIT.equals(offer.getLimitType())) {
            if(offer.getNewLimit() <= account.getAccountLimit()) {
                throw new ValidationException("New limit should be greater than old limit");
            }
        } else {
            if(offer.getNewLimit() <= account.getPerTransactionLimit()) {
                throw new ValidationException("New limit should be greater than old limit");
            }
            if(offer.getNewLimit() > account.getAccountLimit()) {
                throw new ValidationException("Per transaction limit should be greater than account limit");
            }
        }

        String offerActivationTime = offer.getOfferActivationTime();
        String offerExpiryTime = offer.getOfferExpiryTime();

        if (DateTimeUtil.isValidDate(offerActivationTime) || DateTimeUtil.isValidDate(offerExpiryTime)) {
            throw new ValidationException("Not a valid date format");
        }

//        if()
    }
}
