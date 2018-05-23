package com.test.utils.responses;

import lombok.Getter;

@Getter
public class BalanceResponce {
    private final Double balance;

    public BalanceResponce(Double balance) {
        this.balance = balance;
    }
}
