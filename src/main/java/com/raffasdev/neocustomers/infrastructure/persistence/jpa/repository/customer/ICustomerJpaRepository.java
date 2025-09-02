package com.raffasdev.neocustomers.infrastructure.persistence.jpa.repository.customer;

import com.raffasdev.neocustomers.infrastructure.persistence.jpa.entity.CustomerEntity;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ICustomerJpaRepository extends JpaRepository<CustomerEntity, UUID> {

    Page<CustomerEntity> findByNameIgnoreCaseContaining(@NonNull String name, @NonNull Pageable pageable);

    boolean existsByEmail(String email);

    boolean existsByCpf(String cpf);
}
