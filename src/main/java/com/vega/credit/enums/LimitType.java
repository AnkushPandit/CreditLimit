package com.vega.credit.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum LimitType {
    ACCOUNT_LIMIT("ACCOUNT_LIMIT"),
    PER_TRANSACTION_LIMIT("PER_TRANSACTION_LIMIT");

    private final String limitType;
}
