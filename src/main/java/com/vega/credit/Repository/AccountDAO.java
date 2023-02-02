package com.vega.credit.Repository;

import com.vega.credit.model.Account;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class AccountDAO {
    private static final Accounts accountList = new Accounts();

    static {
        accountList.getAccountList().add(
                Account.builder().
                accountId("1234").
                accountLimit(10000).
                lastAccountLimit(0).
                perTransactionLimit(2000).
                lastPerTransactionLimit(0).
                customerId("Riju").
                build());
    }

    public Accounts getAllAccounts(){
        return accountList;
    }

    public List<Account> getAccount(String accountId) {
        return accountList.
                getAccountList().
                stream().
                filter(account -> account.getAccountId().equals(accountId)).
                collect(Collectors.toList());
    }

    public void addAccount(Account account) {
        accountList.getAccountList().add(account);
    }
}
