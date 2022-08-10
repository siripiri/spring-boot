package com.siripiri.example.hibernate.dao;

import com.siripiri.example.hibernate.domain.Driver;

import java.util.List;
import java.util.Optional;

public interface DriverDao {
    List<Driver> findDriverLikeLastName(String likeLastName);

    Driver findByFirstName(String firstName);

    Driver findByLastName(String lastName);

    Optional<List<Driver>> findAll();

    Driver findByName(String firstName, String lastName);

    Driver findById(Long id);

    Driver saveDriver(Driver Driver);
}
