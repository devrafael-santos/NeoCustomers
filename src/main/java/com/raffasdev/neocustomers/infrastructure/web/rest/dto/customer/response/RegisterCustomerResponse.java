package com.raffasdev.neocustomers.infrastructure.web.rest.dto.customer.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.util.UUID;

public record RegisterCustomerResponse(UUID id, String name, String email, String cpf, String phone,
                                       @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy") LocalDate birthDate) {
}
