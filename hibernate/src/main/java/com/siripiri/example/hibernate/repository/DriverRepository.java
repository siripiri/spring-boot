package com.siripiri.example.hibernate.repository;

import com.siripiri.example.hibernate.domain.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriverRepository extends JpaRepository<Driver, Long> {
}
