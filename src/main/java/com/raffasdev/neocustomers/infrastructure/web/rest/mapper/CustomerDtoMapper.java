package com.raffasdev.neocustomers.infrastructure.web.rest.mapper;

import com.raffasdev.neocustomers.domain.model.customer.Customer;
import com.raffasdev.neocustomers.infrastructure.web.rest.dto.customer.response.GetCustomerResponse;
import com.raffasdev.neocustomers.infrastructure.web.rest.dto.customer.response.RegisterCustomerResponse;
import org.springframework.stereotype.Component;

@Component
public class CustomerDtoMapper {

    public RegisterCustomerResponse toRegisterCustomerResponse(Customer customer) {
        return new RegisterCustomerResponse(
                customer.getId().getValue(),
                customer.getName(),
                customer.getEmail(),
                customer.getCPF(),
                customer.getPhone(),
                customer.getBirthDate()
        );
    }

    public GetCustomerResponse toCustomerGetResponse(Customer customer) {
        return new GetCustomerResponse(
                customer.getId().getValue(),
                customer.getName(),
                customer.getEmail(),
                customer.getCPF(),
                customer.getPhone(),
                customer.getAge()
        );
    }

}
