package com.siripiri.example.hibernate.relationship.mappings.example1.repositories;

import com.siripiri.example.hibernate.relationship.mappings.example1.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
