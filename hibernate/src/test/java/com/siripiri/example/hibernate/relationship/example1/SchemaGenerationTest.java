package com.siripiri.example.hibernate.relationship.example1;

import com.siripiri.example.hibernate.relationship.mappings.example1.domain.*;
import com.siripiri.example.hibernate.relationship.mappings.example1.repositories.OrderHeaderRepository;
import com.siripiri.example.hibernate.relationship.mappings.example1.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Set;

@DataJpaTest
@ActiveProfiles("local")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class SchemaGenerationTest {

    @Autowired
    OrderHeaderRepository orderHeaderRepository;

    @Autowired
    ProductRepository productRepository;

    Product product;

    @BeforeEach
    void setUp() {
        Product product1 = new Product();
        product1.setDescription("Test Product");
        product1.setProductStatus(ProductStatus.NEW);
        product = productRepository.saveAndFlush(product1);
    }

    /*
     * When you associate both the OrderHeader and OrderLine and save the OrderHeader.
     * You may get the stackoverflow error because you're validating both the hashcode and moves into a loop.
     * So remove the orderHeader property in OrderLine's hash code.
     *
     * If you ran the test in debug mode you will get OrderLine id as null.
     * Flush() --> flush the data's from cache to db
     * If you even give orderHeaderRepository.flush() you will get the same null value.
     * It may insert the OrderHeader but still OrderLine will be empty.
     *
     * Reason:
     *  Here we're building up the association, but we are not mentioned orderLine to persist the data into the database.
     *  That’s why we got a null value in orderLine id.
     *
     * To mention this we need to use the cascade option: OrderHeader.java
     *      @OneToMany(mappedBy = "orderHeader", cascade = CascadeType.PERSIST)
     *      Private Set<OrderLine> orderLines;
     *
     *  This will tell to hibernate to insert the OrderLine to the database whenever association happens.
     */
    @Test
    void testOrderHeaderRelationship() {
        OrderHeader orderHeader = new OrderHeader();
        orderHeader.setOrderStatus(OrderStatus.NEW);
        orderHeader.setCustomer("Prasath");

        OrderLine orderLine = new OrderLine();
        orderLine.setQuantityOrdered(5);
        orderLine.setProduct(product);
        orderLine.setOrderHeader(orderHeader);

        orderHeader.setOrderLines(Set.of(orderLine));
        OrderHeader savedOrderHeader = orderHeaderRepository.save(orderHeader);

        orderHeaderRepository.flush();
        assertNotNull(savedOrderHeader);
        assertNotNull(savedOrderHeader.getId());
        assertNotNull(savedOrderHeader.getOrderStatus());
        assertNotNull(savedOrderHeader.getOrderLines());
        assertEquals(savedOrderHeader.getOrderLines().size(), 1);

        OrderHeader fetchedOrder = orderHeaderRepository.findById(savedOrderHeader.getId()).orElse(null);

        assertNotNull(fetchedOrder);
        assertEquals(fetchedOrder.getOrderLines().size(), 1);
    }
}
