package com.raffasdev.neocustomers.domain.model.customer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface ICustomerRepository {

    Customer save(Customer customer);

    Page<Customer> findAll(Pageable pageable);

    Page<Customer> searchByName(String name, Pageable pageable);

    Optional<Customer> findById(UUID id);

    void updateById(UUID id, Customer customer);

    void deleteById(UUID id);
}
