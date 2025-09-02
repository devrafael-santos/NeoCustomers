package com.raffasdev.neocustomers.infrastructure.web.rest.controller;

import com.raffasdev.neocustomers.domain.model.customer.Customer;
import com.raffasdev.neocustomers.domain.model.customer.ICustomerService;
import com.raffasdev.neocustomers.infrastructure.web.rest.dto.customer.request.RegisterCustomerRequest;
import com.raffasdev.neocustomers.infrastructure.web.rest.dto.customer.request.UpdateCustomerRequest;
import com.raffasdev.neocustomers.infrastructure.web.rest.dto.customer.response.GetCustomerResponse;
import com.raffasdev.neocustomers.infrastructure.web.rest.dto.customer.response.RegisterCustomerResponse;
import com.raffasdev.neocustomers.infrastructure.web.rest.mapper.CustomerDtoMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "2. Clientes", description = "Endpoints para gerenciamento de clientes")
@SecurityRequirement(name = "bearerAuth")
public class CustomerController {

    private final ICustomerService customerService;

    private final CustomerDtoMapper customerDtoMapper;

    @Operation(summary = "Cria um novo cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cliente criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados de requisição inválidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado (permissão insuficiente)"),
            @ApiResponse(responseCode = "409", description = "CPF ou Email já cadastrado")
    })
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

    @Operation(
            summary = "Lista clientes de forma paginada ou busca por nome",
            description = "Retorna uma lista paginada de todos os clientes. Opcionalmente, pode filtrar pelo nome."
    )
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso") })
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

    @Operation(summary = "Busca um cliente por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado para o ID fornecido")
    })
    @GetMapping("/{id}")
    public ResponseEntity<GetCustomerResponse> findById(@PathVariable UUID id) {

        return  ResponseEntity.ok(customerDtoMapper.toCustomerGetResponse(customerService.findById(id)));
    }

    @Operation(summary = "Atualiza um cliente existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cliente atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados de requisição inválidos"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado para o ID fornecido")
    })
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

    @Operation(summary = "Exclui um cliente existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cliente excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado para o ID fornecido")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable UUID id) {

        customerService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
