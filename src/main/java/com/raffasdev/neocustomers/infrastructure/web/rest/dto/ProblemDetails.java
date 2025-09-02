package com.raffasdev.neocustomers.infrastructure.web.rest.dto;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.Instant;

@Data
@SuperBuilder
public class ProblemDetails {
    private String title;
    private int status;
    private String details;
    private Instant timestamp;
}