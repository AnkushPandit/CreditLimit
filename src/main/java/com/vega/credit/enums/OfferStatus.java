package com.vega.credit.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum OfferStatus {
    ACCEPT("ACCEPT"),
    REJECT("REJECT"),
    PENDING("PENDING");

    private final String offerStatus;
}
