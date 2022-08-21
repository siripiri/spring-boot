package com.siripiri.example.hibernate.inheritance.single;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.math.BigDecimal;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class DebitAccount extends Account {
    public Long overDraftFee;

    public DebitAccount(String owner, BigDecimal balance, BigDecimal interestRate, Long overDraftFee) {
        super(owner, balance, interestRate);
        this.overDraftFee = overDraftFee;
    }
}
