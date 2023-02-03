package com.vega.credit.handler;

import com.vega.credit.dao.AccountDAO;
import com.vega.credit.model.Account;
import com.vega.credit.util.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AccountHandler {
    @Autowired
    private AccountDAO accountDAO;

    public Account fetchAccountInformation(String accountId) {
        Account account = accountDAO.getAccount(accountId);
        if(!Optional.ofNullable(account).isPresent()) {
            System.out.printf("No account with account id: %s is found%n", accountId);
            return null;
        }
        return account;
    }

    public String createAccount(String customerId, int accountLimit, int perTransactionLimit) {
        /* Using current timestamp as account id which makes it unique and can be used as primary key
        * This is for assignment purpose, originally a hash function can be used */
        String accountId = DateTimeUtil.getCurrentTimeStamp();

        Account account = Account.builder().customerId(customerId).
                accountId(accountId).
                accountLimit(accountLimit).
                perTransactionLimit(perTransactionLimit).
                lastAccountLimit(0).
                lastPerTransactionLimit(0).
                accountLimitUpdateTime(DateTimeUtil.getCurrentDate()).
                perTransactionLimitUpdateTime(DateTimeUtil.getCurrentDate()).
                build();
        accountDAO.addAccount(account);
        return accountId;
    }
}
