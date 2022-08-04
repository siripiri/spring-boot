package com.siripiri.example.hibernate.data;

import com.siripiri.example.hibernate.dao.LocationDao;
import com.siripiri.example.hibernate.dao.LocationDaoImpl;
import com.siripiri.example.hibernate.domain.Location;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@ActiveProfiles("local")
@Import(LocationDaoImpl.class)
public class DaoIntegrationTest {

    @Autowired
    LocationDao locationDao;

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
}
