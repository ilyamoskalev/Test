package com.test.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "accounts")
@Getter
@Setter
public class AccountEntity {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    private String id;

    @Column(name = "balance", nullable = false, columnDefinition = "DECIMAL(10, 2) DEFAULT '0.00'")
    private double balance;

    @SuppressWarnings("unused")
    public AccountEntity() {
    }

    public AccountEntity(String id) {
        this.id = id;
    }
}
