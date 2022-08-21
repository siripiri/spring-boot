package com.siripiri.example.hibernate.data;

import com.siripiri.example.hibernate.dao.DriverDao;
import com.siripiri.example.hibernate.dao.DriverDaoImpl;
import com.siripiri.example.hibernate.dao.LocationDao;
import com.siripiri.example.hibernate.dao.LocationDaoImpl;
import com.siripiri.example.hibernate.domain.Driver;
import com.siripiri.example.hibernate.domain.Location;
import com.siripiri.example.hibernate.inheritance.joinedTable.ElectricGuitar;
import com.siripiri.example.hibernate.inheritance.joinedTable.repository.ElectricGuitarRepository;
import com.siripiri.example.hibernate.inheritance.single.Account;
import com.siripiri.example.hibernate.inheritance.single.CreditAccount;
import com.siripiri.example.hibernate.inheritance.single.DebitAccount;
import com.siripiri.example.hibernate.inheritance.single.dao.AccountDao;
import com.siripiri.example.hibernate.inheritance.single.dao.AccountDaoImpl;
import com.siripiri.example.hibernate.repository.DriverRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@ActiveProfiles("local")
@Import({LocationDaoImpl.class, DriverDaoImpl.class, AccountDaoImpl.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class DaoIntegrationTest {

    @Autowired
    LocationDao locationDao;

    @Autowired
    DriverDao driverDao;

    @Autowired
    DriverRepository driverRepository;

    @Autowired
    AccountDao accountDao;

    @Autowired
    ElectricGuitarRepository electricGuitarRepository;

    @Test
    void testDeleteLocation() {
        Location location = new Location();
        location.setName("john");
        location.setKmAllocated(100L);

        Location saved = locationDao.saveNewLocation(location);

        locationDao.deleteLocationById(saved.getId());


        Location deleted = locationDao.getById(saved.getId());
        assertThat(deleted).isNull();

        assertThat(locationDao.getById(saved.getId()));
    }

    @Test
    void testUpdateLocation() {
        Location location = new Location();
        location.setName("john");
        location.setKmAllocated(100L);

        Location saved = locationDao.saveNewLocation(location);

        saved.setName("Thompson");
        Location updated = locationDao.updateLocation(saved);

        assertThat(updated.getName()).isEqualTo("Thompson");
    }

    @Test
    void testSaveLocation() {
        Location location = new Location();
        location.setName("John");
        location.setKmAllocated(100L);
        Location saved = locationDao.saveNewLocation(location);

        assertThat(saved).isNotNull();
        assertThat(saved.getId()).isNotNull();
    }

    @Test
    void testGetLocationByNameAndKmAllocated() {
        Location location = locationDao.findLocationByNameAndKmAllocated("Erode", 250L);

        assertThat(location).isNotNull();
    }

    @Test
    void testGetLocation() {

        Location location = locationDao.getById(1L);

        assertThat(location).isNotNull();

    }

    @Test
    void testFindDriverLikeLastName() {
        List<Driver> driverList = driverDao.findDriverLikeLastName("C");

        assertThat(driverList).isNotNull();
        assertThat(driverList.size()).isGreaterThan(0);
    }

    /*
     * Save:
     * Driver is from Repository
     * Driver5 is from DAO
     *
     *  findByFirstName:
     *  Driver3 is find obj by DAO and saved by Repository  ||| Failed
     *  Driver2 is find obj by Repository and saved by Repository  ||| passed
     *  Driver6 is find obj by DAO and saved by DAO ||| Passed
     *
     *  Suspecting that Repository saveAndFlush() is taking from first level cache and not commited it
     *  Where in DAO since we're using entityManager.getTransaction().commit(); it commits to the DB, so it works
     */
    @Test
    void testFindByFirstName() {
        Driver driver = new Driver();
        driver.setFirstName("Naveen");
        driver.setLastName("B S");

        Driver driver4 = new Driver();
        driver4.setFirstName("Lelouch");
        driver4.setLastName("Vi Britania");

        Driver savedDriver = driverRepository.saveAndFlush(driver);

        Driver driver2 = driverRepository.findById(2L).orElse(null);
        Driver driver3 = driverDao.findByFirstName("Naveen");
        Driver driver5 = driverDao.saveDriver(driver4);
        Driver driver6 = driverDao.findByFirstName("Lelouch");

        assertThat(driver2).isNotNull();
        assertThat(driver2.getFirstName()).isEqualTo("Naveen");

        //assertThat(driver3).isNotNull();
        //assertThat(driver3.getFirstName()).isEqualTo("Naveen");

        assertThat(driver6.getFirstName()).isEqualTo("Lelouch");
    }

    @Test
    void testFindAll() {
        List<Driver> driverList = driverDao.findAll().orElse(null);

        assertThat(driverList).isNotNull();
        assertThat(driverList.size()).isGreaterThan(0);
    }

    @Test
    void testFindByName() {
        Driver driver = driverDao.findByName("Sriprasath", "C P");

        assertThat(driver).isNotNull();
        assertThat(driver.getId()).isEqualTo(1L);
        assertThat(driver.getLastName()).isEqualTo("C P");
    }

    @Test
    void testFindById() {
        Driver driver = driverDao.findById(1L);

        assertThat(driver).isNotNull();
        assertThat(driver.getId()).isEqualTo(1L);
    }

    @Test
    void testFindAllByPagination() {
        // PageRequest.of( page: 0, size: 10)
        List<Driver> driverList = driverDao.findAll(PageRequest.of(0,10));

        assertThat(driverList).isNotNull();
        assertThat(driverList.size()).isGreaterThan(9);
        assertThat(driverList.size()).isEqualTo(10);
    }

    @Test
    void testFindAllSortByFirstName() {
        List<Driver> driverList = driverDao.findAllSortByFirstName(PageRequest.of(0,10, Sort.by(Sort.Order.desc("firstName"))));

        assertThat(driverList).isNotNull();
        assertThat(driverList.size()).isEqualTo(10);
        assertThat(driverList.get(0).getFirstName()).isEqualTo("ken");
    }

    /*
     *  If you find all the account query it will give only the credit and debit class list because the account is
     *  abstract class
     *
     *  [CreditAccount(creditLimit=400000.00), DebitAccount(overDraftFee=400000.00)]
     *
     */

   @Test
   void testPolymorphicQueries() {
        accountDao.polyMorphicQueries();
        List<Account> accounts = accountDao.findAll();

        CreditAccount creditAccount = (CreditAccount) accounts.get(0);
        DebitAccount debitAccount = (DebitAccount) accounts.get(1);

        assertThat(creditAccount.getCreditLimit()).isEqualTo(400000L);
        assertThat(debitAccount.getOverDraftFee()).isEqualTo(400000L);
   }

   /*
    * JOINED TABLE:
    *  In joined table inheritance type. We will use inner join to search the table for the inherited once.
    *  EXAMPLE:
    *
    */
    @Test
    void testJoinedTable() {
        ElectricGuitar electricGuitar = new ElectricGuitar();
        electricGuitar.setNumberOfStrings(10);
        electricGuitar.setNumberOfPickups(10);
        electricGuitarRepository.save(electricGuitar);
        List<ElectricGuitar> electricGuitars = electricGuitarRepository.findAll();
        ElectricGuitar electricGuitar1 = electricGuitars.get(0);

        assertThat(electricGuitar1.getNumberOfPickups()).isEqualTo(10);
        assertThat(electricGuitar1.getNumberOfStrings()).isEqualTo(10);
        assertThat(electricGuitar1.getId()).isEqualTo(1L);
    }

}
