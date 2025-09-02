package com.raffasdev.neocustomers.infrastructure.web.rest.dto.customer.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@AllArgsConstructor
@Data
public class RegisterCustomerRequest {

    @NotBlank(message = "Name of Customer cannot be blank")
    @Size(min = 2, max = 15, message = "Name of Customer must be between 2 and 15 characters")
    private String name;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "CPF cannot be blank")
    @Pattern(
            regexp = "^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$",
            message = "The CPF format should be xxx.xxx.xxx-xx"
    )
    private String cpf;

    @NotBlank(message = "Phone cannot be blank")
    @Pattern(regexp = "^(\\(?\\d{2}\\)?\\s?)?(9?\\d{4}[-.\\s]?\\d{4})$",
            message = "Invalid phone format"
    )
    private String phone;

    @NotNull(message = "Birthdate cannot be blank")
    @Past(message = "Birthdate should be in the past")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate birthDate;

}
