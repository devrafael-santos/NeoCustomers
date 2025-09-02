package com.raffasdev.neocustomers.infrastructure.persistence.jpa.entity;

import com.raffasdev.neocustomers.domain.model.customer.valueObject.BirthDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CustomerEntityTest {

    @Test
    @DisplayName("create should return a CustomerEntity with the provided parameters")
    void create_ReturnsCustomerEntityWithProvidedParameters() {
        var customerEntity = CustomerEntity.create(
                UUID.randomUUID(),
                "username",
                "teste@gmail.com",
                "111.111.111-11",
                "12345671",
                LocalDate.now().minusYears(20)
        );

        assertNotNull(customerEntity);
        assertNotNull(customerEntity.getCustomerId());
        assertEquals("username", customerEntity.getName());
        assertEquals("teste@gmail.com", customerEntity.getEmail());
        assertEquals("111.111.111-11", customerEntity.getCpf());
        assertEquals("12345671", customerEntity.getPhone());
        assertEquals(LocalDate.now().minusYears(20), customerEntity.getBirthDate());
    }

    @Test
    @DisplayName("default constructor should create an empty CustomerEntity")
    void defaultConstructor_CreatesEmptyCustomerEntity() {
        var customerEntity = new CustomerEntity();

        assertNotNull(customerEntity);
        assertNull(customerEntity.getCustomerId());
        assertNull(customerEntity.getName());
        assertNull(customerEntity.getEmail());
        assertNull(customerEntity.getCpf());
        assertNull(customerEntity.getPhone());
        assertNull(customerEntity.getBirthDate());
    }

}