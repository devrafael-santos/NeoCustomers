package com.raffasdev.neocustomers.application.service;

import com.raffasdev.neocustomers.application.exception.CPFAlreadyExistsException;
import com.raffasdev.neocustomers.application.exception.CustomerNotFoundException;
import com.raffasdev.neocustomers.application.exception.EmailAlreadyExistsException;
import com.raffasdev.neocustomers.domain.model.customer.Customer;
import com.raffasdev.neocustomers.domain.model.customer.valueObject.BirthDate;
import com.raffasdev.neocustomers.domain.model.customer.valueObject.CPF;
import com.raffasdev.neocustomers.domain.model.customer.valueObject.Phone;
import com.raffasdev.neocustomers.domain.model.shared.valueObject.Email;
import com.raffasdev.neocustomers.domain.model.shared.valueObject.EntityId;
import com.raffasdev.neocustomers.domain.model.shared.valueObject.Name;
import com.raffasdev.neocustomers.infrastructure.persistence.jpa.repository.customer.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepositoryMock;

    @InjectMocks
    private CustomerService customerService;

    private Customer customer;

    @BeforeEach
    void setUp() {
        customer = Customer.create(
                EntityId.newId(),
                Name.newName("username"),
                Email.newEmail("teste@email.com"),
                CPF.newCPF("111.222.333-44"),
                Phone.newPhone("11911112222"),
                BirthDate.newBirthDate(LocalDate.now().minusYears(20))
        );
    }

    @Test
    @DisplayName("save should return a Customer when data is valid and not duplicate")
    void save_returnsCustomer_whenDataIsValidAndNotDuplicate() {

        given(customerRepositoryMock.existsByEmail(any(String.class))).willReturn(false);
        given(customerRepositoryMock.existsByCpf(any(String.class))).willReturn(false);
        given(customerRepositoryMock.save(any(Customer.class))).willReturn(customer);

        Customer savedCustomer = customerService.save(
                "username",
                "teste@email.com",
                "111.222.333-44",
                "11911112222",
                LocalDate.now().minusYears(20)
        );

        assertThat(savedCustomer).isNotNull();
        assertThat(customer).isEqualTo(savedCustomer);
        verify(customerRepositoryMock, times(1)).save(any(Customer.class));
    }

    @Test
    @DisplayName("save should throw EmailAlreadyExistsException when email already exists")
    void save_throwsEmailAlreadyExistsException_whenEmailAlreadyExists() {

        String email = "teste@email.com";
        given(customerRepositoryMock.existsByEmail(email)).willReturn(true);

        assertThatThrownBy(() -> customerService.save(
                "username", email,
                "111.222.333-44",
                "11911112222",
                LocalDate.now().minusYears(20)))
                .isInstanceOf(EmailAlreadyExistsException.class);

        verify(customerRepositoryMock, never()).save(any(Customer.class));
    }

    @Test
    @DisplayName("save throws CPFAlreadyExistsException when CPF already exists")
    void save_throwsCPFAlreadyExistsException_whenCpfAlreadyExists() {

        String cpf = "111.222.333-44";
        given(customerRepositoryMock.existsByEmail(anyString())).willReturn(false);
        given(customerRepositoryMock.existsByCpf(cpf)).willReturn(true);

        assertThatThrownBy(() -> customerService.save(
                "username",
                "teste@email.com",
                cpf,
                "11911112222",
                LocalDate.now().minusYears(20)))
                .isInstanceOf(CPFAlreadyExistsException.class);

        verify(customerRepositoryMock, never()).save(any(Customer.class));
    }

    @Test
    @DisplayName("findById should return a Customer when id exists")
    void findById_returnsCustomer_whenIdExists() {

        given(customerRepositoryMock.findById(any(UUID.class))).willReturn(Optional.of(customer));

        Customer foundCustomer = customerService.findById(customer.getId().getValue());

        assertThat(foundCustomer).isNotNull();
        assertThat(foundCustomer).isEqualTo(customer);
    }

    @Test
    @DisplayName("findById should throw CustomerNotFoundException when id does not exist")
    void findById_throwsCustomerNotFoundException_whenIdDoesNotExist() {

        UUID nonExistentId = UUID.randomUUID();
        given(customerRepositoryMock.findById(nonExistentId)).willReturn(Optional.empty());

        assertThatThrownBy(() -> customerService.findById(nonExistentId))
                .isInstanceOf(CustomerNotFoundException.class);
    }

    @Test
    @DisplayName("findAll should return a Page of Customers")
    void findAll_returnsPageOfCustomers_always() {

        Pageable pageable = Pageable.unpaged();
        Page<Customer> customerPage = new PageImpl<>(List.of(customer));
        given(customerRepositoryMock.findAll(pageable)).willReturn(customerPage);

        Page<Customer> result = customerService.findAll(pageable);

        assertThat(result).isNotEmpty();
        assertThat(result.getTotalElements()).isEqualTo(1);
    }

    @Test
    @DisplayName("searchByName should return a Page of Customers when a name is provided")
    void searchByName_returnsPageOfCustomers_whenNameIsProvided() {

        String nameToSearch = "user";
        Pageable pageable = Pageable.unpaged();
        Page<Customer> expectedPage = new PageImpl<>(List.of(customer));

        given(customerRepositoryMock.searchByName(nameToSearch, pageable)).willReturn(expectedPage);

        Page<Customer> actualPage = customerService.searchByName(nameToSearch, pageable);

        assertThat(actualPage).isNotEmpty();
        assertThat(actualPage.getTotalElements()).isEqualTo(1);
        assertThat(actualPage.getContent().get(0).getName()).isEqualTo(customer.getName());

        verify(customerRepositoryMock).searchByName(nameToSearch, pageable);
    }

    @Test
    @DisplayName("deleteById should call repository's delete method")
    void deleteById_callsRepositoryDelete_always() {

        UUID idToDelete = UUID.randomUUID();
        willDoNothing().given(customerRepositoryMock).deleteById(idToDelete);

        customerService.deleteById(idToDelete);

        verify(customerRepositoryMock, times(1)).deleteById(idToDelete);
    }

    @Test
    @DisplayName("updateById should update customer when data is valid")
    void updateById_updatesCustomer_whenDataIsValid() {

        UUID customerId = customer.getId().getValue();
        String newName = "newUsername";
        String newEmail = "new_teste@teste.com";
        String newPhone = "11933334444";
        LocalDate newBirthDate = LocalDate.now().minusYears(20);

        given(customerRepositoryMock.findById(customerId)).willReturn(Optional.of(customer));
        given(customerRepositoryMock.existsByEmail(newEmail)).willReturn(false);
        given(customerRepositoryMock.save(any(Customer.class))).willReturn(customer);

        customerService.updateById(customerId, newName, newEmail, newPhone, newBirthDate);

        verify(customerRepositoryMock, times(1))
                .save(any(Customer.class));
        verify(customerRepositoryMock, times(1)).save(any(Customer.class));
    }

    @Test
    @DisplayName("updateById should throw CustomerNotFoundException when customer to update is not found")
    void updateById_throwsCustomerNotFoundException_whenCustomerIsNotFound() {

        UUID nonExistentId = UUID.randomUUID();
        given(customerRepositoryMock.findById(nonExistentId)).willReturn(Optional.empty());

        assertThatThrownBy(() -> customerService.updateById(nonExistentId,
                "username",
                "teste@email.com",
                "11911112222",
                LocalDate.now().minusYears(20)))
                .isInstanceOf(CustomerNotFoundException.class);

        verify(customerRepositoryMock, never()).save(any());
    }

    @Test
    @DisplayName("updateById should throw EmailAlreadyExistsException when new email is already taken")
    void updateById_throwsEmailAlreadyExistsException_whenNewEmailIsTaken() {

        String newEmail = "existing@email.com";
        given(customerRepositoryMock.findById(customer.getId().getValue())).willReturn(Optional.of(customer));
        given(customerRepositoryMock.existsByEmail(newEmail)).willReturn(true);

        assertThatThrownBy(() -> customerService.updateById(customer.getId().getValue(),
                "username",
                newEmail,
                "11911112222",
                LocalDate.now().minusYears(20)))
                .isInstanceOf(EmailAlreadyExistsException.class);

        verify(customerRepositoryMock, never()).save(any());
    }
}