package com.vega.credit.model;

import com.vega.credit.enums.LimitType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class Offer {
    private String accountId;

    private LimitType limitType;

    private int newLimit;

    private String offerActivationTime;

    private String offerExpiryTime;
}
