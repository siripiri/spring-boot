package com.siripiri.example.hibernate.data;

import com.siripiri.example.hibernate.dao.domain.Location;
import com.siripiri.example.hibernate.repository.LocationRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Commit;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DataJpaTest
public class SpringBootJpaSpliceTest {
    @Autowired
    LocationRepository locationRepository;

    @Commit
    @Order(1)
    @Test
    void testLocationRepository() {
        Long countBefore = locationRepository.count();
        locationRepository.save(new Location(2L, "Coimbatore", 350L));
        Long countAfter = locationRepository.count();
        assertThat(countBefore).isLessThan(countAfter);
    }

    @Order(2)
    @Test
    void testLocationRepositoryTransactional() {
        Long count = locationRepository.count();
        Location location = locationRepository.findById(2L).orElse(null);
        assertThat(count).isEqualTo(2L);
        assertThat(location.getId()).isEqualTo(2L);
        assertThat(location.getName()).isEqualTo("Coimbatore");
    }
}
