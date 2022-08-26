package com.siripiri.example.hibernate.relationship.mappings.example1.repositories;

import com.siripiri.example.hibernate.relationship.mappings.example1.domain.OrderHeader;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderHeaderRepository extends JpaRepository<OrderHeader, Long> {
}
