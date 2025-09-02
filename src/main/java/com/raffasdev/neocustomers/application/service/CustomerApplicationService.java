package com.raffasdev.neocustomers.application.service;

import com.raffasdev.neocustomers.application.exception.CPFAlreadyExistsException;
import com.raffasdev.neocustomers.application.exception.CustomerNotFoundException;
import com.raffasdev.neocustomers.application.exception.EmailAlreadyExistsException;
import com.raffasdev.neocustomers.domain.model.customer.Customer;
import com.raffasdev.neocustomers.domain.model.customer.ICustomerApplicationService;
import com.raffasdev.neocustomers.domain.model.customer.valueObject.BirthDate;
import com.raffasdev.neocustomers.domain.model.customer.valueObject.CPF;
import com.raffasdev.neocustomers.domain.model.customer.valueObject.Phone;
import com.raffasdev.neocustomers.domain.model.shared.valueObject.Email;
import com.raffasdev.neocustomers.domain.model.shared.valueObject.EntityId;
import com.raffasdev.neocustomers.domain.model.shared.valueObject.Name;
import com.raffasdev.neocustomers.infrastructure.persistence.jpa.repository.customer.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CustomerApplicationService implements ICustomerApplicationService {

    private final CustomerRepository customerRepository;

    @Transactional
    @Override
    public Customer save(String name, String email, String cpf, String phone, LocalDate birthDate) {

        if (customerRepository.existsByEmail(email)) {
            throw new EmailAlreadyExistsException(email);
        }

        if (customerRepository.existsByCpf(cpf)) {
            throw new CPFAlreadyExistsException(cpf);
        }

        Customer customer = Customer.create(
                EntityId.newId(),
                Name.newName(name),
                Email.newEmail(email),
                CPF.newCPF(cpf),
                Phone.newPhone(phone),
                BirthDate.newBirthDate(birthDate)
        );

        return customerRepository.save(customer);
    }

    @Override
    public Page<Customer> findAll(Pageable pageable) {

        return customerRepository.findAll(pageable);
    }

    @Override
    public Page<Customer> searchByName(String name, Pageable pageable) {

        return customerRepository.searchByName(name, pageable);
    }

    @Override
    public Customer findById(UUID id) {

        return customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));
    }

    @Transactional
    @Override
    public void updateById(UUID id, String name, String email, String phone, LocalDate birthDate) {

        Customer currentCustomer = this.findById(id);

        Name nameToUpdate = (name != null && !name.isBlank()) ? Name.newName(name) :
                Name.newName(currentCustomer.getName());

        Phone phoneToUpdate = (phone != null && !phone.isBlank()) ?
                Phone.newPhone(phone) : Phone.newPhone(currentCustomer.getPhone());

        BirthDate birthDateToUpdate = (birthDate != null) ?
                BirthDate.newBirthDate(birthDate) : BirthDate.newBirthDate(currentCustomer.getBirthDate());

        Email emailToUpdate = Email.newEmail(currentCustomer.getEmail());
        if (email != null && !email.isBlank() && !currentCustomer.getEmail().equals(email)) {

            if (customerRepository.existsByEmail(email)) {
                throw new EmailAlreadyExistsException(email);
            }

            emailToUpdate = Email.newEmail(email);
        }


        Customer customerUpdated = Customer.reconstitute(
                currentCustomer.getId(),
                nameToUpdate,
                emailToUpdate,
                CPF.newCPF(currentCustomer.getCPF()),
                phoneToUpdate,
                birthDateToUpdate
        );

        customerRepository.save(customerUpdated);
    }

    @Override
    public void deleteById(UUID id) {

        customerRepository.deleteById(id);
    }
}
