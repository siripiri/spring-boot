package com.siripiri.example.hibernate.inheritance.single.dao;

import com.siripiri.example.hibernate.inheritance.single.Account;

import java.util.List;

public interface AccountDao {

    void polyMorphicQueries();

    List<Account> findAll();

}
