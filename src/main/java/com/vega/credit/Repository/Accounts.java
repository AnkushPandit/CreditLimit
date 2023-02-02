package com.vega.credit.Repository;

import com.vega.credit.model.Account;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class Accounts {
    private List<Account> accountList = new ArrayList<>();
}
