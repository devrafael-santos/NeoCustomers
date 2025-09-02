package com.raffasdev.neocustomers.infrastructure.web.rest.dto.customer.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@AllArgsConstructor
@Data
public class UpdateCustomerRequest {

    @Size(min = 2, max = 15, message = "Name of Customer must be between 2 and 15 characters")
    private String name;

    @Email(message = "Invalid email format")
    private String email;

    @Pattern(regexp = "^(\\(?\\d{2}\\)?\\s?)?(9?\\d{4}[-.\\s]?\\d{4})$",
            message = "Invalid phone format"
    )
    private String phone;

    @Past(message = "Birthdate should be in the past")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate birthDate;
}
