package com.raffasdev.neocustomers.infrastructure.web.rest.dto.customer.response;

import java.util.UUID;

public record GetCustomerResponse(UUID id, String name, String email, String cpf, String phone, Integer age) {
}
