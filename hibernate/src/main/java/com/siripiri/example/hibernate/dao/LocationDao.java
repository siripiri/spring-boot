package com.siripiri.example.hibernate.dao;

import com.siripiri.example.hibernate.domain.Location;

public interface LocationDao {
    Location getById(Long id);

    Location findLocationByNameAndKmAllocated(String name, Long kmAllocated);

    Location saveNewLocation(Location location);

    Location updateLocation(Location location);

    void deleteLocationById(Long id);
}
