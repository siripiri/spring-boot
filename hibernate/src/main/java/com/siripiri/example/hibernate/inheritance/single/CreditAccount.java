package com.siripiri.example.hibernate.inheritance.single;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.math.BigDecimal;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class CreditAccount extends Account{
    public Long creditLimit;

    public CreditAccount(String owner, BigDecimal balance, BigDecimal interestRate, Long creditLimit) {
        super(owner, balance, interestRate);
        this.creditLimit = creditLimit;
    }
}

