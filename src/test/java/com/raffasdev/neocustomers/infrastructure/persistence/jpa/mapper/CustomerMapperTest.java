package com.raffasdev.neocustomers.infrastructure.persistence.jpa.mapper;

import com.raffasdev.neocustomers.domain.model.customer.Customer;
import com.raffasdev.neocustomers.domain.model.customer.valueObject.CPF;
import com.raffasdev.neocustomers.domain.model.customer.valueObject.Phone;
import com.raffasdev.neocustomers.domain.model.shared.valueObject.Email;
import com.raffasdev.neocustomers.domain.model.shared.valueObject.EntityId;
import com.raffasdev.neocustomers.domain.model.shared.valueObject.Name;
import com.raffasdev.neocustomers.infrastructure.persistence.jpa.entity.CustomerEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CustomerMapperTest {

    private CustomerMapper customerMapper;

    @BeforeEach
    void setUp() {

        customerMapper = new CustomerMapper();
    }

    @Test
    @DisplayName("toEntity should map all fields from Customer to CustomerEntity correctly")
    void toEntity_shouldMapAllFields_fromDomainToEntity() {

        var id = EntityId.newId();
        Customer customerDomain = Customer.create(
                id,
                Name.newName("Username"),
                Email.newEmail("teste@email.com"),
                CPF.of("123.456.789-00"),
                Phone.of("11987654321")
        );

        CustomerEntity resultEntity = customerMapper.toEntity(customerDomain);

        assertNotNull(resultEntity);
        assertEquals(id.getValue(), resultEntity.getCustomerId());
        assertEquals("Username", resultEntity.getName());
        assertEquals("teste@email.com", resultEntity.getEmail());
        assertEquals("123.456.789-00", resultEntity.getCpf());
        assertEquals("11987654321", resultEntity.getPhone());
    }

    @Test
    @DisplayName("toDomain should map all fields from CustomerEntity to Customer correctly")
    void toDomain_shouldMapAllFields_fromEntityToDomain() {

        var id = UUID.randomUUID();
        CustomerEntity customerEntity = CustomerEntity.create(
                id,
                "Username",
                "teste@email.com",
                "123.456.789-00",
                "11987654321"
        );

        Customer resultDomain = customerMapper.toDomain(customerEntity);

        assertNotNull(resultDomain);
        assertEquals(id, resultDomain.getId().getValue());
        assertEquals("Username", resultDomain.getName());
        assertEquals("teste@email.com", resultDomain.getEmail());
        assertEquals("123.456.789-00", resultDomain.getCPF());
        assertEquals("11987654321", resultDomain.getPhone());
    }

    @Test
    @DisplayName("toDomain should return null when CustomerEntity is null")
    void toDomain_shouldReturnNull_whenEntityIsNull() {

        assertNull(customerMapper.toDomain(null));
    }

    @Test
    @DisplayName("toOptionalDomain should return Optional Customer when Optional CustomerEntity is provided")
    void toOptionalDomain_shouldReturnOptionalCustomer_whenOptionalEntityIsProvided() {

        var customerEntity = CustomerEntity.create(
                UUID.randomUUID(),
                "Username",
                "teste@email.com",
                "123.456.789-00",
                "11987654321"
        );
        var optionalEntity = Optional.of(customerEntity);

        Optional<Customer> optionalDomain = customerMapper.toOptionalDomain(optionalEntity);

        assertTrue(optionalDomain.isPresent());
        assertEquals(customerEntity.getCustomerId(), optionalDomain.get().getId().getValue());
    }

    @Test
    @DisplayName("toOptionalDomain should return an empty Optional when Optional CustomerEntity is empty")
    void toOptionalDomain_shouldReturnEmptyOptional_whenOptionalEntityIsEmpty() {

        Optional<CustomerEntity> emptyOptional = Optional.empty();

        Optional<Customer> resultOptional = customerMapper.toOptionalDomain(emptyOptional);

        assertTrue(resultOptional.isEmpty());
    }
}