package com.raffasdev.neocustomers.domain.model.customer;

import com.raffasdev.neocustomers.domain.model.customer.valueObject.BirthDate;
import com.raffasdev.neocustomers.domain.model.customer.valueObject.CPF;
import com.raffasdev.neocustomers.domain.model.customer.valueObject.Phone;
import com.raffasdev.neocustomers.domain.model.shared.valueObject.Email;
import com.raffasdev.neocustomers.domain.model.shared.valueObject.EntityId;
import com.raffasdev.neocustomers.domain.model.shared.valueObject.Name;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {

    private Customer customer;
    private Customer reconstitutedCustomer;
    private EntityId id;
    private Name name;
    private Email email;
    private CPF cpf;
    private Phone phone;
    private BirthDate birthDate;

    @BeforeEach
    void setUp() {
        id = EntityId.newId();
        name = Name.newName("username");
        email = Email.newEmail("teste@email.com");
        cpf = CPF.of("123.456.789-00");
        phone = Phone.of("11987654321");
        birthDate = BirthDate.of(LocalDate.now().minusYears(20));

        customer = Customer.create(id, name, email, cpf, phone, birthDate);
        reconstitutedCustomer = Customer.reconstitute(id, name, email, cpf, phone, birthDate);
    }

    @Test
    @DisplayName("create should return a valid Customer when correct state is provided")
    void create_returnsValidCustomer_WhenCorrectStateIsProvided() {

        assertNotNull(customer);
        assertEquals(id, customer.getId());
        assertEquals(name.getValue(), customer.getName());
        assertEquals(email.getValue(), customer.getEmail());
        assertEquals(cpf.getValue(), customer.getCPF());
        assertEquals(phone.getValue(), customer.getPhone());
        assertEquals(birthDate.getValue(), customer.getBirthDate());
    }

    @Test
    @DisplayName("reconstitute should return a valid Customer when correct state is provided")
    void reconstitute_returnsValidCustomer_WhenCorrectStateIsProvided() {

        assertNotNull(reconstitutedCustomer);
        assertEquals(id, reconstitutedCustomer.getId());
        assertEquals(customer.getName(), reconstitutedCustomer.getName());
        assertEquals(customer.getEmail(), reconstitutedCustomer.getEmail());
        assertEquals(customer.getCPF(), reconstitutedCustomer.getCPF());
        assertEquals(customer.getPhone(), reconstitutedCustomer.getPhone());
        assertEquals(customer.getBirthDate(), reconstitutedCustomer.getBirthDate());
        assertEquals(customer.getAge(), reconstitutedCustomer.getAge());
    }

    @Test
    @DisplayName("hasName should return true when name matches")
    void hasName_returnsTrue_WhenNameMatches() {

        assertTrue(customer.hasName(Name.newName("username")));
    }

    @Test
    @DisplayName("hasName should return false when name does not match")
    void hasName_returnsFalse_WhenNameDoesNotMatch() {

        assertFalse(customer.hasName(Name.newName("Another Name")));
    }

    @Test
    @DisplayName("hasEmail should return true when email matches")
    void hasEmail_returnsTrue_WhenEmailMatches() {

        assertTrue(customer.hasEmail(Email.newEmail("teste@email.com")));
    }

    @Test
    @DisplayName("hasEmail should return false when email does not match")
    void hasEmail_returnsFalse_WhenEmailDoesNotMatch() {

        assertFalse(customer.hasEmail(Email.newEmail("another@email.com")));
    }

    @Test
    @DisplayName("hasCPF should return true when CPF matches")
    void hasCPF_returnsTrue_WhenCpfMatches() {

        assertTrue(customer.hasCPF(CPF.of("123.456.789-00")));
    }

    @Test
    @DisplayName("hasCPF should return false when CPF does not match")
    void hasCPF_returnsFalse_WhenCpfDoesNotMatch() {

        assertFalse(customer.hasCPF(CPF.of("111.222.333-44")));
    }

    @Test
    @DisplayName("hasPhone should return true when phone matches")
    void hasPhone_returnsTrue_WhenPhoneMatches() {

        assertTrue(customer.hasPhone(Phone.of("11987654321")));
    }

    @Test
    @DisplayName("hasPhone should return false when phone does not match")
    void hasPhone_returnsFalse_WhenPhoneDoesNotMatch() {

        assertFalse(customer.hasPhone(Phone.of("9988887777")));
    }

    @Test
    @DisplayName("hasBirthdate should return true when Birthdate matches")
    void hasBirthdate_returnsTrue_WhenBirthdateMatches() {

        assertTrue(customer.hasBirthDate(birthDate));
    }

    @Test
    @DisplayName("hasBirthdate should return false when Birthdate does not match")
    void hasBirthdate_returnsFalse_WhenBirthdateDoesNotMatch() {

        assertFalse(customer.hasBirthDate(BirthDate.of(LocalDate.now().minusYears(25))));
    }

    @Test
    @DisplayName("getters should return the correct primitive values always")
    void getters_returnCorrectPrimitiveValues_Always() {

        assertEquals("username", customer.getName());
        assertEquals("teste@email.com", customer.getEmail());
        assertEquals("123.456.789-00", customer.getCPF());
        assertEquals("11987654321", customer.getPhone());
        assertEquals(birthDate.getValue(), customer.getBirthDate());
    }

    @Test
    @DisplayName("equals should return true when Customer objects are equal")
    void equals_returnsTrue_WhenCustomerObjectsAreEqual() {

        Customer anotherCustomer = Customer.create(
                id,
                Name.newName("Another Name"),
                Email.newEmail("another@email.com"),
                CPF.of("111.222.333-44"),
                Phone.of("9988887777"),
                BirthDate.of(LocalDate.now().minusYears(20))
        );

        assertEquals(customer, anotherCustomer);
        assertEquals(customer.hashCode(), anotherCustomer.hashCode());
    }

    @Test
    @DisplayName("equals should return false when Customer objects are not equal")
    void equals_returnsFalse_WhenCustomerObjectsAreNotEqual() {

        Customer anotherCustomer = Customer.create(
                EntityId.newId(), name, email, cpf, phone, birthDate
        );

        assertNotEquals(customer, anotherCustomer);
        assertNotEquals(customer.hashCode(), anotherCustomer.hashCode());
    }
}