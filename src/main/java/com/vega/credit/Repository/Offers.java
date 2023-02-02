package com.vega.credit.Repository;

import com.vega.credit.model.Offer;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Offers {
    private List<Offer> offerList = new ArrayList<>();
}
