package com.raffasdev.neocustomers.infrastructure.web.rest.handler;

import com.raffasdev.neocustomers.application.exception.*;
import com.raffasdev.neocustomers.domain.exception.*;
import com.raffasdev.neocustomers.infrastructure.web.rest.dto.ProblemDetails;
import com.raffasdev.neocustomers.infrastructure.web.rest.dto.ValidationProblemDetails;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException exception, HttpHeaders headers, HttpStatusCode status, WebRequest request) {


        ProblemDetails exceptionDetails = ProblemDetails.builder()
                .timestamp(Instant.now())
                .status(status.value())
                .title("Message Not Readable Exception: Invalid fields syntax")
                .details(exception.getCause().getMessage())
                .build();

        return this.createResponseEntity(exceptionDetails, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        List<FieldError> fieldErrors = exception.getFieldErrors();
        String fields = fieldErrors.stream().map(FieldError::getField).collect(Collectors.joining(" | "));
        String fieldsMessage = fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(" | "));

        return new ResponseEntity<>(
                ValidationProblemDetails.builder()
                        .timestamp(Instant.now())
                        .status(HttpStatus.BAD_REQUEST.value())
                        .title("Bad Request Exception: Invalid Fields")
                        .details("Check the field(s) error")
                        .fields(fields)
                        .fieldsMessage(fieldsMessage)
                        .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CPFAlreadyExistsException.class)
    public ResponseEntity<Object> handleCPFAlreadyExistsException(CPFAlreadyExistsException exception, WebRequest request) {
        ProblemDetails exceptionDetails = ProblemDetails.builder()
                .timestamp(Instant.now())
                .status(HttpStatus.CONFLICT.value())
                .title("Already Exists Exception: CPF already exists")
                .details(exception.getMessage())
                .build();

        return this.createResponseEntity(exceptionDetails, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<Object> handleEmailAlreadyExistsException(EmailAlreadyExistsException exception, WebRequest request) {
        ProblemDetails exceptionDetails = ProblemDetails.builder()
                .timestamp(Instant.now())
                .status(HttpStatus.CONFLICT.value())
                .title("Already Exists Exception: Email already exists")
                .details(exception.getMessage())
                .build();

        return this.createResponseEntity(exceptionDetails, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<Object> handleCustomerNotFoundException(CustomerNotFoundException exception, WebRequest request) {
        ProblemDetails exceptionDetails = ProblemDetails.builder()
                .timestamp(Instant.now())
                .status(HttpStatus.NOT_FOUND.value())
                .title("Not Found Exception: Customer not found")
                .details(exception.getMessage())
                .build();

        return this.createResponseEntity(exceptionDetails, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException exception, WebRequest request) {
        ProblemDetails exceptionDetails = ProblemDetails.builder()
                .timestamp(Instant.now())
                .status(HttpStatus.NOT_FOUND.value())
                .title("Not Found Exception: User not found")
                .details(exception.getMessage())
                .build();

        return this.createResponseEntity(exceptionDetails, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(WrongCredentialsException.class)
    public ResponseEntity<Object> handleWrongCredentialsException(WrongCredentialsException exception, WebRequest request) {
        ProblemDetails exceptionDetails = ProblemDetails.builder()
                .timestamp(Instant.now())
                .status(HttpStatus.UNAUTHORIZED.value())
                .title("Wrong Credentials Exception: E-mail and/or Password incorrect")
                .details(exception.getMessage())
                .build();

        return this.createResponseEntity(exceptionDetails, new HttpHeaders(), HttpStatus.UNAUTHORIZED, request);
    }

    @ExceptionHandler(InvalidBirthDateException.class)
    public ResponseEntity<Object> handleInvalidBirthDateException(InvalidBirthDateException exception, WebRequest request) {
        ProblemDetails exceptionDetails = ProblemDetails.builder()
                .timestamp(Instant.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .title("Invalid Field Exception: Invalid Birth Date")
                .details(exception.getMessage())
                .build();

        return this.createResponseEntity(exceptionDetails, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(InvalidCPFException.class)
    public ResponseEntity<Object> handleInvalidCPFException(InvalidCPFException exception, WebRequest request) {
        ProblemDetails exceptionDetails = ProblemDetails.builder()
                .timestamp(Instant.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .title("Invalid Field Exception: Invalid CPF")
                .details(exception.getMessage())
                .build();

        return this.createResponseEntity(exceptionDetails, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(InvalidEmailException.class)
    public ResponseEntity<Object> handleInvalidEmailException(InvalidEmailException exception, WebRequest request) {
        ProblemDetails exceptionDetails = ProblemDetails.builder()
                .timestamp(Instant.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .title("Invalid Field Exception: Invalid Email")
                .details(exception.getMessage())
                .build();

        return this.createResponseEntity(exceptionDetails, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(InvalidNameException.class)
    public ResponseEntity<Object> handleInvalidNameException(InvalidNameException exception, WebRequest request) {
        ProblemDetails exceptionDetails = ProblemDetails.builder()
                .timestamp(Instant.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .title("Invalid Field Exception: Invalid Name")
                .details(exception.getMessage())
                .build();

        return this.createResponseEntity(exceptionDetails, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(InvalidPhoneException.class)
    public ResponseEntity<Object> handleInvalidPhoneException(InvalidPhoneException exception, WebRequest request) {
        ProblemDetails exceptionDetails = ProblemDetails.builder()
                .timestamp(Instant.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .title("Invalid Field Exception: Invalid Phone")
                .details(exception.getMessage())
                .build();

        return this.createResponseEntity(exceptionDetails, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

}
