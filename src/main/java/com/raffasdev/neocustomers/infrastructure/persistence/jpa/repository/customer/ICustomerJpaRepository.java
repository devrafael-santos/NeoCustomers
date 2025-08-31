package com.raffasdev.neocustomers.infrastructure.persistence.jpa.repository.customer;

import com.raffasdev.neocustomers.domain.model.customer.Customer;
import com.raffasdev.neocustomers.infrastructure.persistence.jpa.entity.CustomerEntity;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface ICustomerJpaRepository extends JpaRepository<CustomerEntity, UUID> {

    Page<CustomerEntity> findByNameIgnoreCaseContaining(@NonNull String name, @NonNull Pageable pageable);

    void updateCustomerByCustomerId(@NonNull UUID id, Customer customer);

}
