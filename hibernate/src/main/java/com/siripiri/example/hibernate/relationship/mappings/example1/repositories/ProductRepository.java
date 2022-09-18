package com.siripiri.example.hibernate.relationship.mappings.example1.repositories;

import com.siripiri.example.hibernate.relationship.mappings.example1.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByDescription(String description);
}
