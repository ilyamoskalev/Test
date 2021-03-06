package com.test.dao;

import com.test.entities.AccountEntity;

public interface AccountDAO {
    AccountEntity getById(String id);

    void create(String id);

    void deposit(String id, Double summ);

    void withdraw(String id, Double summ);

    Double getBalance(String id);

    void clear();
}
