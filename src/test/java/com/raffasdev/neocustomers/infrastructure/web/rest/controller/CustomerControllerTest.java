package com.raffasdev.neocustomers.infrastructure.web.rest.controller;

import com.raffasdev.neocustomers.domain.model.customer.Customer;
import com.raffasdev.neocustomers.domain.model.customer.ICustomerService;
import com.raffasdev.neocustomers.infrastructure.web.rest.dto.customer.request.RegisterCustomerRequest;
import com.raffasdev.neocustomers.infrastructure.web.rest.dto.customer.request.UpdateCustomerRequest;
import com.raffasdev.neocustomers.infrastructure.web.rest.dto.customer.response.GetCustomerResponse;
import com.raffasdev.neocustomers.infrastructure.web.rest.dto.customer.response.RegisterCustomerResponse;
import com.raffasdev.neocustomers.infrastructure.web.rest.mapper.CustomerDtoMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {

    @Mock
    private ICustomerService customerServiceMock;

    @Mock
    private CustomerDtoMapper customerDtoMapperMock;

    @InjectMocks
    private CustomerController customerController;

    @Test
    @DisplayName("createCustomer should return status 201 and the response DTO")
    void createCustomer_shouldReturnCreated_whenSuccessful() {

        var requestDto = new RegisterCustomerRequest(
                "John Doe",
                "johndoe@email.com",
                "123.456.789-00",
                "11987654321",
                LocalDate.now().minusYears(20)
        );

        Customer customerDomain = mock(Customer.class);
        var responseDto = new RegisterCustomerResponse(
                UUID.randomUUID(),
                "John Doe",
                "johndoe@email.com",
                "123.456.789-00",
                "11987654321",
                LocalDate.now().minusYears(20)
        );

        given(customerServiceMock.save(any(), any(), any(), any(), any())).willReturn(customerDomain);
        given(customerDtoMapperMock.toRegisterCustomerResponse(customerDomain)).willReturn(responseDto);

        ResponseEntity<RegisterCustomerResponse> response = customerController.createCustomer(requestDto);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(responseDto);
    }

    @Test
    @DisplayName("findAll should return status 200 OK and a mapped page of customers")
    void findAll_shouldReturnOkAndPage_whenCalled() {

        Pageable pageable = Pageable.unpaged();
        Page<Customer> customerPage = new PageImpl<>(List.of(mock(Customer.class)));

        given(customerServiceMock.findAll(pageable)).willReturn(customerPage);
        given(customerDtoMapperMock.toCustomerGetResponse(any(Customer.class))).willReturn(mock(GetCustomerResponse.class));

        ResponseEntity<Page<GetCustomerResponse>> response = customerController.findAll(null, pageable);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getTotalElements()).isEqualTo(1);
        verify(customerServiceMock, never()).searchByName(anyString(), any(Pageable.class));
    }

    @Test
    @DisplayName("findAll should call searchByName service method when name parameter is provided")
    void findAll_callsSearchByName_whenNameParameterIsProvided() {

        String nameToSearch = "John Doe";
        Pageable pageable = Pageable.unpaged();

        Page<Customer> customerPage = new PageImpl<>(List.of(mock(Customer.class)));

        given(customerServiceMock.searchByName(nameToSearch, pageable)).willReturn(customerPage);

        given(customerDtoMapperMock.toCustomerGetResponse(any(Customer.class))).willReturn(mock(GetCustomerResponse.class));

        ResponseEntity<Page<GetCustomerResponse>> response = customerController.findAll(nameToSearch, pageable);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();

        verify(customerServiceMock, times(1)).searchByName(nameToSearch, pageable);
        verify(customerServiceMock, never()).findAll(any(Pageable.class));
    }

    @Test
    @DisplayName("findById should return status 200 OK and customer response DTO")
    void findById_shouldReturnOkAndDto_whenCustomerExists() {

        UUID customerId = UUID.randomUUID();
        Customer customerDomain = mock(Customer.class);
        var responseDto = new GetCustomerResponse(
                customerId,
                "John Doe",
                "johndoe@email.com",
                "123.456.789-00",
                "11987654321",
                20
        );

        given(customerServiceMock.findById(customerId)).willReturn(customerDomain);
        given(customerDtoMapperMock.toCustomerGetResponse(customerDomain)).willReturn(responseDto);

        ResponseEntity<GetCustomerResponse> response = customerController.findById(customerId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(responseDto);
    }

    @Test
    @DisplayName("updateById should return status 204 No Content")
    void updateById_shouldReturnNoContent_whenSuccessful() {

        UUID customerId = UUID.randomUUID();
        var requestDto = new UpdateCustomerRequest(
                "John Doe",
                "johndoe@email.com",
                "11987654321", LocalDate.now().minusYears(25)
        );
        willDoNothing().given(customerServiceMock).updateById(any(), any(), any(), any(), any());

        ResponseEntity<Void> response = customerController.updateById(customerId, requestDto);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        verify(customerServiceMock).updateById(any(), any(), any(), any(), any());
    }

    @Test
    @DisplayName("deleteById should return status 204 No Content")
    void deleteById_shouldReturnNoContent_whenSuccessful() {

        UUID customerId = UUID.randomUUID();
        willDoNothing().given(customerServiceMock).deleteById(customerId);

        ResponseEntity<Void> response = customerController.deleteById(customerId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        verify(customerServiceMock).deleteById(customerId);
    }

}