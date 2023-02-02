package com.vega.credit.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class Account {
    private String accountId;

    private String customerId;

    private int accountLimit;

    private int perTransactionLimit;

    private int lastAccountLimit;

    private int lastPerTransactionLimit;

    private String accountLimitUpdateTime;

    private String perTransactionLimitUpdateTime;
}
