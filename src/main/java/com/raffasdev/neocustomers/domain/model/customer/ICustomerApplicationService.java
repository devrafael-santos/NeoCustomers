package com.raffasdev.neocustomers.domain.model.customer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.UUID;

public interface ICustomerApplicationService {

    Customer save(String name, String email, String cpf, String phone, LocalDate birthDate);

    Page<Customer> findAll(Pageable pageable);

    Page<Customer> searchByName(String name, Pageable pageable);

    Customer findById(UUID id);

    void updateById(UUID id, String name, String email, String phone, LocalDate birthDate);

    void deleteById(UUID id);

}
