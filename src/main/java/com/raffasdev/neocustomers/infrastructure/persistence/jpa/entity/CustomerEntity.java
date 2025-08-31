package com.raffasdev.neocustomers.infrastructure.persistence.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

import java.util.UUID;

@Entity
@Table(name = "customers")
@Getter
public class CustomerEntity {

    @Id
    @Column(name = "customer_id")
    private UUID customerId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "cpf", nullable = false, unique = true)
    private String cpf;

    @Column(name = "phone", nullable = false)
    private String phone;

    public CustomerEntity() {
    }

    private CustomerEntity(UUID customerId, String name, String email, String cpf, String phone) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.cpf = cpf;
        this.phone = phone;
    }

    public static CustomerEntity create(UUID customerId, String name, String email, String cpf, String phone) {
        return new CustomerEntity(customerId, name, email, cpf, phone);
    }
}
