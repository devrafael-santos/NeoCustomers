package com.raffasdev.neocustomers.infrastructure.web.rest.dto;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class ValidationProblemDetails extends ProblemDetails {
    private final String fields;
    private final String fieldsMessage;
}
