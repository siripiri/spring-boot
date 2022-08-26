package com.siripiri.example.hibernate.dao;

import com.siripiri.example.hibernate.dao.domain.Driver;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface DriverDao {
    List<Driver> findDriverLikeLastName(String likeLastName);

    Driver findByFirstName(String firstName);

    Driver findByLastName(String lastName);

    Optional<List<Driver>> findAll();

    Driver findByName(String firstName, String lastName);

    Driver findById(Long id);

    List<Driver> findAll(Pageable pageable);

    List<Driver> findAllSortByFirstName(Pageable pageable);

    Driver saveDriver(Driver Driver);
}
