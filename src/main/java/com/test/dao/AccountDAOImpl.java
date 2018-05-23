package com.test.dao;

import com.test.entities.AccountEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class AccountDAOImpl implements AccountDAO {
    private final EntityManager entityManager;

    public AccountDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public AccountEntity getById(String id) {
        return entityManager.find(AccountEntity.class, id);
    }

    @Override
    public void create(String id) {
        entityManager.persist(new AccountEntity(id));
    }

    @Override
    public void deposit(String id, Double summ) {
        entityManager.createQuery("UPDATE AccountEntity SET balance = balance + :summ WHERE id = :id")
                .setParameter("summ", summ)
                .setParameter("id", id)
                .executeUpdate();
    }

    @Override
    public void withdraw(String id, Double summ) {
        entityManager.createQuery("UPDATE AccountEntity SET balance = balance - :summ WHERE id = :id")
                .setParameter("summ", summ)
                .setParameter("id", id)
                .executeUpdate();
    }

    @Override
    public Double getBalance(String id) {
        return (Double) entityManager.createQuery("SELECT e.balance FROM AccountEntity e WHERE id = :id")
                .setParameter("id", id)
                .getSingleResult();
    }
}
