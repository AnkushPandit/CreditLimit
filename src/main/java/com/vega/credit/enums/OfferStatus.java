package com.vega.credit.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum OfferStatus {
    ACCEPT("ACCEPTED"),
    REJECT("REJECTED"),
    PENDING("PENDING");

    private final String offerStatus;
}
