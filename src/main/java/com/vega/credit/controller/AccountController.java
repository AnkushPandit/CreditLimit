package com.vega.credit.controller;

import com.vega.credit.handler.AccountHandler;
import com.vega.credit.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import static com.vega.credit.constants.AppConstants.DEFAULT_ACCOUNT_LIMIT;
import static com.vega.credit.constants.AppConstants.DEFAULT_PER_TRANSACTION_LIMIT;


@RestController
@RequestMapping(path = "/account")
@Service
public class AccountController {

    @Autowired
    private AccountHandler accountHandler;

    @PostMapping(path = "/create",
            produces = "application/json")
    public ResponseEntity<Object> createAccount(
            @RequestParam(value = "customerId") String customerId,
            @RequestParam(value = "accountLimit", required = false) Integer accountLimit,
            @RequestParam(value = "perTransactionLimit", required = false) Integer perTransactionLimit) {
        try {
            accountLimit =
                    (accountLimit == null) ? DEFAULT_ACCOUNT_LIMIT : accountLimit;
            perTransactionLimit =
                    (perTransactionLimit == null) ? DEFAULT_PER_TRANSACTION_LIMIT : perTransactionLimit;
            String accountId =
                    accountHandler.createAccount(customerId, accountLimit, perTransactionLimit);
            return ResponseEntity.ok().body(String.format("Account with id: %s has been created", accountId));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping(path = "/get", produces = "application/json")
    public ResponseEntity<Object> getAccountInformation(@RequestParam(value = "accountId")String accountId) {
        try {
            Account account = accountHandler.fetchAccountInformation(accountId);
            if (!Optional.ofNullable(account).isPresent()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok().body(account);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
