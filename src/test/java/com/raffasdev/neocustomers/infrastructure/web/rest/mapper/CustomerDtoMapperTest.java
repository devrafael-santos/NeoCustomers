package com.raffasdev.neocustomers.infrastructure.web.rest.mapper;

import com.raffasdev.neocustomers.domain.model.customer.Customer;
import com.raffasdev.neocustomers.domain.model.customer.valueObject.BirthDate;
import com.raffasdev.neocustomers.domain.model.customer.valueObject.CPF;
import com.raffasdev.neocustomers.domain.model.customer.valueObject.Phone;
import com.raffasdev.neocustomers.domain.model.shared.valueObject.Email;
import com.raffasdev.neocustomers.domain.model.shared.valueObject.EntityId;
import com.raffasdev.neocustomers.domain.model.shared.valueObject.Name;
import com.raffasdev.neocustomers.infrastructure.web.rest.dto.customer.response.GetCustomerResponse;
import com.raffasdev.neocustomers.infrastructure.web.rest.dto.customer.response.RegisterCustomerResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat; // Usando AssertJ para asserções mais legíveis

class CustomerDtoMapperTest {

    private CustomerDtoMapper customerDtoMapper;
    private Customer customer;

    @BeforeEach
    void setUp() {

        this.customerDtoMapper = new CustomerDtoMapper();

        this.customer = Customer.create(

                EntityId.newId(),
                Name.newName("username"),
                Email.newEmail("teste@email.com"),
                CPF.newCPF("123.456.789-00"),
                Phone.newPhone("11987654321"),
                BirthDate.newBirthDate(LocalDate.now().minusYears(20))
        );
    }

    @Test
    @DisplayName("toRegisterCustomerResponse should map all fields correctly")
    void toRegisterCustomerResponse_shouldMapAllFieldsCorrectly_whenSuccessful() {

        RegisterCustomerResponse response = customerDtoMapper.toRegisterCustomerResponse(customer);

        assertThat(response).isNotNull();
        assertThat(response.id()).isEqualTo(customer.getId().getValue());
        assertThat(response.name()).isEqualTo(customer.getName());
        assertThat(response.email()).isEqualTo(customer.getEmail());
        assertThat(response.cpf()).isEqualTo(customer.getCPF());
        assertThat(response.phone()).isEqualTo(customer.getPhone());
        assertThat(response.birthDate()).isEqualTo(customer.getBirthDate());
    }

    @Test
    @DisplayName("toCustomerGetResponse should map all fields correctly and calculate the age")
    void toCustomerGetResponse_shouldMapAllFieldsAndAge_whenSuccessful() {

        GetCustomerResponse response = customerDtoMapper.toCustomerGetResponse(customer);

        assertThat(response).isNotNull();
        assertThat(response.id()).isEqualTo(customer.getId().getValue());
        assertThat(response.name()).isEqualTo(customer.getName());
        assertThat(response.email()).isEqualTo(customer.getEmail());
        assertThat(response.cpf()).isEqualTo(customer.getCPF());
        assertThat(response.phone()).isEqualTo(customer.getPhone());
        assertThat(response.age()).isEqualTo(customer.getAge());
    }
}