package com.siripiri.example.hibernate.inheritance.single.dao;

import com.siripiri.example.hibernate.inheritance.single.Account;
import com.siripiri.example.hibernate.inheritance.single.CreditAccount;
import com.siripiri.example.hibernate.inheritance.single.DebitAccount;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.util.List;

@Component
public class AccountDaoImpl implements AccountDao {
    private final EntityManagerFactory entityManagerFactory;

    public AccountDaoImpl(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    private EntityManager getEntityManagerFactory() {
        return entityManagerFactory.createEntityManager();
    }

    /*
     * Polymorphism is nothing but ability to display more than one form
     */
    public void polyMorphicQueries() {
        EntityManager entityManager = getEntityManagerFactory();
        try{
            entityManager.getTransaction().begin();

            Account creditAccount = new CreditAccount("Sriprasath", new BigDecimal(100000),
                    new BigDecimal(7),400000L);

            Account debitAccount = new DebitAccount("Sriprasath", new BigDecimal(100000),
                    new BigDecimal(7), 400000L);

            entityManager.persist(creditAccount);
            entityManager.persist(debitAccount);

            entityManager.flush();
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }

    public List<Account> findAll(){
        EntityManager entityManager = getEntityManagerFactory();
        try{
            TypedQuery<Account> query = entityManager.createNamedQuery("get_all_account", Account.class);
            return query.getResultList();
        } catch (NoResultException ee){
            return null;
        } finally {
            entityManager.close();
        }
    }
}
