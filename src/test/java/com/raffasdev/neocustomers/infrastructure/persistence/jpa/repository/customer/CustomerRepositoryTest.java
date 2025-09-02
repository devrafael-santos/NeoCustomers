package com.raffasdev.neocustomers.infrastructure.persistence.jpa.repository.customer;

import com.raffasdev.neocustomers.domain.model.customer.Customer;
import com.raffasdev.neocustomers.domain.model.customer.valueObject.BirthDate;
import com.raffasdev.neocustomers.domain.model.customer.valueObject.CPF;
import com.raffasdev.neocustomers.domain.model.customer.valueObject.Phone;
import com.raffasdev.neocustomers.domain.model.shared.valueObject.Email;
import com.raffasdev.neocustomers.domain.model.shared.valueObject.EntityId;
import com.raffasdev.neocustomers.domain.model.shared.valueObject.Name;
import com.raffasdev.neocustomers.infrastructure.persistence.jpa.entity.CustomerEntity;
import com.raffasdev.neocustomers.infrastructure.persistence.jpa.mapper.CustomerMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CustomerRepositoryTest {

    @Mock
    private ICustomerJpaRepository jpaRepositoryMock;

    @Mock
    private CustomerMapper customerMapperMock;

    @InjectMocks
    private CustomerRepository customerRepository;

    @Test
    @DisplayName("save should return a mapped domain Customer when save is successful")
    void save_returnsMappedCustomer_whenSaveIsSuccessful() {

        Customer customerToSave = Customer.create(
                EntityId.newId(),
                Name.newName("Test"),
                Email.newEmail("test@test.com"),
                CPF.of("123.456.789-00"),
                Phone.of("11999998888"),
                BirthDate.of(LocalDate.now().minusYears(20))
        );

        CustomerEntity customerEntity = new CustomerEntity();
        CustomerEntity savedEntity = new CustomerEntity();
        Customer expectedSavedCustomer = Customer.reconstitute(
                EntityId.newId(),
                Name.newName("Test"),
                Email.newEmail("test@test.com"),
                CPF.of("123.456.789-00"),
                Phone.of("11999998888"),
                BirthDate.of(LocalDate.now().minusYears(20))
        );

        given(customerMapperMock.toEntity(customerToSave)).willReturn(customerEntity);
        given(jpaRepositoryMock.save(customerEntity)).willReturn(savedEntity);
        given(customerMapperMock.toDomain(savedEntity)).willReturn(expectedSavedCustomer);

        Customer actualSavedCustomer = customerRepository.save(customerToSave);

        assertNotNull(actualSavedCustomer);
        assertEquals(expectedSavedCustomer, actualSavedCustomer);
        verify(customerMapperMock, times(1)).toEntity(customerToSave);
        verify(jpaRepositoryMock, times(1)).save(customerEntity);
        verify(customerMapperMock, times(1)).toDomain(savedEntity);
    }

    @Test
    @DisplayName("findAll should return a Page of mapped domain Customers")
    void findAll_returnsPageOfCustomers_always() {

        Pageable pageable = Pageable.unpaged();
        CustomerEntity customerEntity = new CustomerEntity();
        Page<CustomerEntity> entityPage = new PageImpl<>(List.of(customerEntity));

        Customer customerDomain = Customer.create(
                EntityId.newId(),
                Name.newName("Test"),
                Email.newEmail("test@test.com"),
                CPF.of("123.456.789-00"),
                Phone.of("11999998888"),
                BirthDate.of(LocalDate.now().minusYears(20))
        );

        Page<Customer> expectedDomainPage = new PageImpl<>(List.of(customerDomain));

        given(jpaRepositoryMock.findAll(pageable)).willReturn(entityPage);
        given(customerMapperMock.toDomain(any(CustomerEntity.class))).willReturn(customerDomain);

        Page<Customer> actualDomainPage = customerRepository.findAll(pageable);

        assertFalse(actualDomainPage.isEmpty());
        assertEquals(1, actualDomainPage.getTotalElements());
        assertEquals(expectedDomainPage.getContent().get(0), actualDomainPage.getContent().get(0));
        verify(jpaRepositoryMock, times(1)).findAll(pageable);
    }

    @Test
    @DisplayName("findById should return Optional of Customer when id exists")
    void findById_returnsOptionalOfCustomer_whenIdExists() {

        UUID customerId = UUID.randomUUID();
        Optional<CustomerEntity> entityOptional = Optional.of(new CustomerEntity());
        Optional<Customer> expectedCustomerOptional = Optional.of(Customer.create(
                EntityId.of(customerId),
                Name.newName("Test"),
                Email.newEmail("test@test.com"),
                CPF.of("123.456.789-00"),
                Phone.of("11999998888"),
                BirthDate.of(LocalDate.now().minusYears(20))
        ));

        given(jpaRepositoryMock.findById(customerId)).willReturn(entityOptional);
        given(customerMapperMock.toOptionalDomain(entityOptional)).willReturn(expectedCustomerOptional);

        Optional<Customer> actualCustomerOptional = customerRepository.findById(customerId);

        assertTrue(actualCustomerOptional.isPresent());
        assertEquals(expectedCustomerOptional.get(), actualCustomerOptional.get());
        verify(jpaRepositoryMock, times(1)).findById(customerId);
        verify(customerMapperMock, times(1)).toOptionalDomain(entityOptional);
    }

    @Test
    @DisplayName("findById should return empty Optional when id does not exist")
    void findById_returnsEmptyOptional_whenIdDoesNotExist() {

        UUID customerId = UUID.randomUUID();
        given(jpaRepositoryMock.findById(customerId)).willReturn(Optional.empty());
        given(customerMapperMock.toOptionalDomain(Optional.empty())).willReturn(Optional.empty());

        Optional<Customer> actualCustomerOptional = customerRepository.findById(customerId);

        assertTrue(actualCustomerOptional.isEmpty());
        verify(jpaRepositoryMock, times(1)).findById(customerId);
    }

    @Test
    @DisplayName("deleteById should call repository delete method")
    void deleteById_callsRepositoryDelete_always() {

        UUID customerId = UUID.randomUUID();

        customerRepository.deleteById(customerId);

        verify(jpaRepositoryMock, times(1)).deleteById(customerId);
    }

    @Test
    @DisplayName("searchByName should return a Page of mapped domain Customers")
    void searchByName_returnsPageOfCustomers_whenNameIsFound() {

        String nameFragment = "te";
        Pageable pageable = Pageable.ofSize(10);
        CustomerEntity customerEntity = new CustomerEntity();
        Page<CustomerEntity> entityPage = new PageImpl<>(List.of(customerEntity));

        Customer customerDomain = Customer.create(
                EntityId.newId(),
                Name.newName("test"),
                Email.newEmail("teste@gmail.com"),
                CPF.of("123.456.789-00"),
                Phone.of("11999998888"),
                BirthDate.of(LocalDate.now().minusYears(20))
        );

        given(jpaRepositoryMock.findByNameIgnoreCaseContaining(nameFragment, pageable)).willReturn(entityPage);
        given(customerMapperMock.toDomain(customerEntity)).willReturn(customerDomain);

        Page<Customer> actualDomainPage = customerRepository.searchByName(nameFragment, pageable);

        assertFalse(actualDomainPage.isEmpty());
        assertEquals(1, actualDomainPage.getTotalElements());
        assertEquals(customerDomain.getName(), actualDomainPage.getContent().get(0).getName());

        verify(jpaRepositoryMock, times(1)).findByNameIgnoreCaseContaining(nameFragment, pageable);
    }

}