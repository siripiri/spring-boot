package com.siripiri.example.hibernate.relationship.mappings.example1.repositories;

import com.siripiri.example.hibernate.relationship.mappings.example1.domain.OrderLine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderLineRepository extends JpaRepository<OrderLine, Long> {
}
