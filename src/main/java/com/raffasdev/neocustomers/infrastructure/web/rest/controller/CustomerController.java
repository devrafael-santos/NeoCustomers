package com.raffasdev.neocustomers.infrastructure.web.rest.controller;

import com.raffasdev.neocustomers.domain.model.customer.Customer;
import com.raffasdev.neocustomers.domain.model.customer.ICustomerService;
import com.raffasdev.neocustomers.infrastructure.web.rest.dto.customer.request.RegisterCustomerRequest;
import com.raffasdev.neocustomers.infrastructure.web.rest.dto.customer.request.UpdateCustomerRequest;
import com.raffasdev.neocustomers.infrastructure.web.rest.dto.customer.response.GetCustomerResponse;
import com.raffasdev.neocustomers.infrastructure.web.rest.dto.customer.response.RegisterCustomerResponse;
import com.raffasdev.neocustomers.infrastructure.web.rest.mapper.CustomerDtoMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final ICustomerService customerService;

    private final CustomerDtoMapper customerDtoMapper;

    @PostMapping
    public ResponseEntity<RegisterCustomerResponse> createCustomer(@Valid @RequestBody
                                                                      RegisterCustomerRequest registerCustomerRequest) {
        Customer customer = customerService.save(
                registerCustomerRequest.getName(),
                registerCustomerRequest.getEmail(),
                registerCustomerRequest.getCpf(),
                registerCustomerRequest.getPhone(),
                registerCustomerRequest.getBirthDate()
        );

        return new ResponseEntity<>(
                customerDtoMapper.toRegisterCustomerResponse(customer),
                HttpStatus.CREATED
        );

    }


    @GetMapping
    public ResponseEntity<Page<GetCustomerResponse>> findAll(@RequestParam(required = false) String name,
                                                             Pageable pageable) {
        Page<Customer> customerPage;

        if (name != null && !name.isBlank()) {

            customerPage = customerService.searchByName(name, pageable);
        } else {

            customerPage = customerService.findAll(pageable);
        }

        return ResponseEntity.ok(customerPage.map(customerDtoMapper::toCustomerGetResponse));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetCustomerResponse> findById(@PathVariable UUID id) {

        return  ResponseEntity.ok(customerDtoMapper.toCustomerGetResponse(customerService.findById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateById(@PathVariable UUID id,
                                           @RequestBody @Valid UpdateCustomerRequest  updateCustomerRequest) {

        customerService.updateById(
                id,
                updateCustomerRequest.getName(),
                updateCustomerRequest.getEmail(),
                updateCustomerRequest.getPhone(),
                updateCustomerRequest.getBirthDate()
        );

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable UUID id) {

        customerService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
