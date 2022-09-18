package com.siripiri.example.hibernate.relationship.mappings.example1.repositories;

import com.siripiri.example.hibernate.relationship.mappings.example1.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findCustomerByCustomerNameIgnoreCase(String customerName);
}
