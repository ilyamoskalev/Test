package dao;

import entities.AccountEntity;

public interface AccountDAO {
    AccountEntity getById(String id);

    void create(String id);

    void deposit(String id, Double summ);

    void withdraw(String id, Double summ);

    Double getBalance(String id);
}
