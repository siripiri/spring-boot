package com.siripiri.example.hibernate.inheritance.single;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

/*
 *  The way the table are created is specified in inheritance_single-changelog.xml
 *  Note:
 *      Since it is single table inheritance type it will create a single table
 *      to differentiate two tables we use "dtype" and the value of the dtype will be entity name.
 */
@Data
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@NoArgsConstructor
@AllArgsConstructor
@NamedQuery(name = "get_all_account",query = "FROM Account")
public abstract class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String owner;

    public BigDecimal balance;

    public BigDecimal interestRate;

    public Account(String owner, BigDecimal balance, BigDecimal interestRate) {
        this.owner = owner;
        this.balance = balance;
        this.interestRate = interestRate;
    }
}
