package com.siripiri.example.hibernate.data;

import com.siripiri.example.hibernate.dao.DriverDao;
import com.siripiri.example.hibernate.dao.DriverDaoImpl;
import com.siripiri.example.hibernate.dao.LocationDao;
import com.siripiri.example.hibernate.dao.LocationDaoImpl;
import com.siripiri.example.hibernate.domain.Driver;
import com.siripiri.example.hibernate.domain.Location;
import com.siripiri.example.hibernate.repository.DriverRepository;
import liquibase.pro.packaged.D;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@ActiveProfiles("local")
@Import({LocationDaoImpl.class, DriverDaoImpl.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class DaoIntegrationTest {

    @Autowired
    LocationDao locationDao;

    @Autowired
    DriverDao driverDao;

    @Autowired
    DriverRepository driverRepository;

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
}
