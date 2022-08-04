package com.siripiri.example.hibernate.data;

import com.siripiri.example.hibernate.repository.LocationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class SDJpaIntroApplicationTest {

    @Autowired
    LocationRepository locationRepository;

    @Test
    void testLocationRepository(){
        long count = locationRepository.count();
        assertThat(count).isGreaterThan(0L);
    }
}
