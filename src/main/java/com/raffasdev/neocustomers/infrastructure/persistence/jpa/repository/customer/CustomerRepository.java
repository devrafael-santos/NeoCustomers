package com.raffasdev.neocustomers.infrastructure.persistence.jpa.repository.customer;

import com.raffasdev.neocustomers.domain.model.customer.Customer;
import com.raffasdev.neocustomers.domain.model.customer.ICustomerRepository;
import com.raffasdev.neocustomers.infrastructure.persistence.jpa.mapper.CustomerMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
public class CustomerRepository implements ICustomerRepository {

    private ICustomerJpaRepository customerJpaRepository;

    private CustomerMapper customerMapper;

    @Override
    public Customer save(Customer customer) {

        return customerMapper.toDomain(customerJpaRepository.save(customerMapper.toEntity(customer)));
    }

    @Override
    public Page<Customer> findAll(Pageable pageable) {

        return customerJpaRepository.findAll(pageable).map(customerMapper::toDomain);
    }

    @Override
    public Page<Customer> searchByName(String name, Pageable pageable) {

        return customerJpaRepository.findByNameIgnoreCaseContaining(name, pageable).map(customerMapper::toDomain);
    }

    @Override
    public Optional<Customer> findById(UUID id) {

        return customerMapper.toOptionalDomain(customerJpaRepository.findById(id));
    }

    @Override
    public void updateById(UUID id, Customer customer) {

        customerJpaRepository.updateCustomerByCustomerId(id, customer);
    }

    @Override
    public void deleteById(UUID id) {

        customerJpaRepository.deleteById(id);
    }
}
