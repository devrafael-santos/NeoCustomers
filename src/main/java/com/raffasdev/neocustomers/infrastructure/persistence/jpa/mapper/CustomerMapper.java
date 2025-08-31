package com.raffasdev.neocustomers.infrastructure.persistence.jpa.mapper;

import com.raffasdev.neocustomers.domain.model.customer.Customer;
import com.raffasdev.neocustomers.domain.model.customer.valueObject.CPF;
import com.raffasdev.neocustomers.domain.model.customer.valueObject.Phone;
import com.raffasdev.neocustomers.domain.model.shared.valueObject.Email;
import com.raffasdev.neocustomers.domain.model.shared.valueObject.EntityId;
import com.raffasdev.neocustomers.domain.model.shared.valueObject.Name;
import com.raffasdev.neocustomers.infrastructure.persistence.jpa.entity.CustomerEntity;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public final class CustomerMapper {

    public CustomerEntity toEntity(Customer customer) {

        return CustomerEntity.create(
                customer.getId().getValue(),
                customer.getName(),
                customer.getEmail(),
                customer.getCPF(),
                customer.getPhone()
        );
    }

    public Customer toDomain(CustomerEntity entity) {

        if (entity == null) {
            return null;
        }
        return Customer.reconstitute(
                EntityId.of(entity.getCustomerId()),
                Name.of(entity.getName()),
                Email.of(entity.getEmail()),
                CPF.of(entity.getCpf()),
                Phone.of(entity.getPhone())
        );
    }

    public Optional<Customer> toOptionalDomain(Optional<CustomerEntity> optionalEntity) {

        if (optionalEntity.isEmpty()) {
            return Optional.empty();
        }

        CustomerEntity customerEntity = optionalEntity.get();
        return Optional.of(this.toDomain(customerEntity));
    }
}
