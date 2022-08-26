package com.siripiri.example.hibernate.dao;

import com.siripiri.example.hibernate.dao.domain.Location;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

@Component
public class LocationDaoImpl implements LocationDao {

    private final EntityManagerFactory entityManagerFactory;

    public LocationDaoImpl(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    /*
     * Here we are asking to hibernate to find class that is mapped with the annotation entity.
     * And ask to find the id property.
     *
     * Here hibernate will generate sql for us to find the id
     *
     * In Logs:
     * 2022-08-03 00:44:01.831  INFO 13028 --- [           main] c.s.e.hibernate.data.DaoIntegrationTest  : Started DaoIntegrationTest in 5.932 seconds (JVM running for 7.881)
     * 2022-08-03 00:44:01.853  INFO 13028 --- [           main] o.s.t.c.transaction.TransactionContext   : Began transaction (1) for test context
     * Hibernate:
     * select
     *      location0_.id as id1_0_0_,
     *      location0_.km_allocated as km_alloc2_0_0_,
     *      location0_.name as name3_0_0_
     * from
     *      location location0_
     * where
     *      location0_.id=?
     *
     * In second line we can see transaction(implicit transaction). spring boot test will do that for us
     * Also Hibernate generates sql, mapping the object and return the desire object
     */
    @Override
    public Location getById(Long id) {
        return getEntityManager().find(Location.class, id);
    }

    /*  1. In this we have a query with a parameters
     *
     *  Typed query is one of the way to pass sql (but in hibernate it is called as jql) to hibernate......   ":" in sql is the way to pass the parameter
     *  then use query.setParameter to initialize the value of parameter
     *
     *  hql use object properties not underlying the database properties
     *
     *  Note: In hql for the properties use the same naming convention used in domain/entity Location class
     */
    @Override
    public Location findLocationByNameAndKmAllocated(String name, Long kmAllocated) {
        TypedQuery<Location> query = getEntityManager().createQuery("SELECT a FROM Location a " +
                "WHERE a.name = :name and a.kmAllocated = :km_allocated", Location.class);
        query.setParameter("name", name);
        query.setParameter("km_allocated", kmAllocated);
        return query.getSingleResult();
    }

    /*
    * Hibernate might not always persist every thing right away to the database. hibernate can withhold the database transaction and also delay the persistence
    *
    * An application-managed entity manager participates in a JTA transaction in one of two ways.
    *   1. If the persistence context is created inside the transaction, the persistence provider will automatically
    *  synchronize the persistence context with the transaction.
    *   2. If the persistence context was created earlier (outside of a transaction or in a transaction that has since ended),
    *  the persistence context can be manually synchronized with the transaction by calling joinTransaction() on the
    *  EntityManager interface. Once synchronized, the persistence context will automatically be flushed when the transaction commits.
    *
    *   entityManager.persist(obj); --> sometimes the hibernate takes time to persist so give
    *   entityManager.flush() --> which forces the hibernate to complete the transaction
    *   This transaction will be outside of the context .. we need to use joinTransaction() or explicitly provide
    *   entityManager.getTreansaction().beign(); at beginning and entityManager.getTransaction().commit() at end to commit in DB
    * */
    @Override
    public Location saveNewLocation(Location location) {
        EntityManager entityManager = getEntityManager();
        //entityManager.joinTransaction();
        entityManager.getTransaction().begin();
        entityManager.persist(location);
        entityManager.flush();
        entityManager.getTransaction().commit();
        return location;
    }

    @Override
    public Location updateLocation(Location location) {
        EntityManager entityManager = getEntityManager();
        entityManager.joinTransaction();
        entityManager.merge(location);
        entityManager.flush();
        // it will clear the fist level cache .. so it directly fetch from db and not from hibernate cache
        entityManager.clear();
        return entityManager.find(Location.class, location.getId());
    }

    @Override
    public void deleteLocationById(Long id) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        Location location = em.find(Location.class, id);
        em.remove(location);
        em.flush();
        em.getTransaction().commit();
    }

    /*
     * Entity manager factory is equivalent of the session factory which is heavy weight and application should have only one session factory
     * So Create a helper method here for entity manager (entity manager equivalent to session which is light weight)
     * In DAO class we don't use Entity Manager
     *
     * Factory Design Pattern Application:
     *      The EntityManagerFactory that has a heavyweight object, but the EntityManager itself is light weight
     * So we are asking the Entity Manager Factory to give us an instance os that
     *
     */
    private EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }
}
