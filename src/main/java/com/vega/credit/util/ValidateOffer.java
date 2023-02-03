package com.vega.credit.util;

import com.vega.credit.dao.AccountDAO;
import com.vega.credit.model.Account;
import com.vega.credit.model.Offer;
import lombok.experimental.UtilityClass;

import javax.xml.bind.ValidationException;
import java.util.Optional;

import static com.vega.credit.enums.LimitType.ACCOUNT_LIMIT;

@UtilityClass
public class ValidateOffer {

    AccountDAO accountDAO;

    public void checkOfferValidity(Offer offer) throws ValidationException, RuntimeException {
		try{
			accountDAO = new AccountDAO();
			String accountId = offer.getAccountId();
			if(!Optional.ofNullable(accountId).isPresent()) {
				throw new ValidationException("Offer must have an associated account Id");
			}

			Account account = accountDAO.getAccount(accountId);
			if(!Optional.ofNullable(account).isPresent()) {
				throw new ValidationException("No valid account is present for this account Id");
			}
			
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

			if (!DateTimeUtil.isValidDate(offerActivationTime) || !DateTimeUtil.isValidDate(offerExpiryTime)) {
				throw new ValidationException("Not a valid date format");
			}
		} catch(Exception e){
			//e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}

//        if()
    }
}
