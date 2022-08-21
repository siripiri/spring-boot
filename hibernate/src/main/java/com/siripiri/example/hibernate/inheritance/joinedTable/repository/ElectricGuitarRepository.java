package com.siripiri.example.hibernate.inheritance.joinedTable.repository;

import com.siripiri.example.hibernate.inheritance.joinedTable.ElectricGuitar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ElectricGuitarRepository extends JpaRepository<ElectricGuitar, Long> {
}
