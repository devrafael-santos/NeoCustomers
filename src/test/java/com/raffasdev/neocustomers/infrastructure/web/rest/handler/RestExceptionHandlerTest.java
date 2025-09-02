package com.raffasdev.neocustomers.infrastructure.web.rest.handler;

import com.raffasdev.neocustomers.application.exception.*;
import com.raffasdev.neocustomers.domain.exception.*;
import com.raffasdev.neocustomers.infrastructure.web.rest.dto.ProblemDetails;
import com.raffasdev.neocustomers.infrastructure.web.rest.dto.ValidationProblemDetails;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class RestExceptionHandlerTest {

    @Mock
    private WebRequest webRequestMock;

    @InjectMocks
    private RestExceptionHandler restExceptionHandler;

    @Test
    @DisplayName("handleHttpMessageNotReadable should return 400 Bad Request")
    void handleHttpMessageNotReadable_shouldReturnBadRequest() {

        Throwable cause = new RuntimeException("Unexpected character ('\"' (code 34))");
        var exception = new HttpMessageNotReadableException("Could not read JSON", cause, mock(org.springframework.http.HttpInputMessage.class));

        ResponseEntity<Object> responseEntity = restExceptionHandler.handleHttpMessageNotReadable(
                exception, new HttpHeaders(), HttpStatus.BAD_REQUEST, webRequestMock);
        ProblemDetails problemDetails = (ProblemDetails) responseEntity.getBody();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(problemDetails).isNotNull();
        assertThat(problemDetails.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(problemDetails.getTitle()).contains("Invalid fields syntax");
        assertThat(problemDetails.getDetails()).isEqualTo("Unexpected character ('\"' (code 34))");
    }

    @Test
    @DisplayName("handleCPFAlreadyExistsException should return 409 Conflict")
    void handleCPFAlreadyExistsException_shouldReturnConflict() {
        var exception = new CPFAlreadyExistsException("123.456.789-00");
        ResponseEntity<Object> responseEntity = restExceptionHandler.handleCPFAlreadyExistsException(exception, webRequestMock);
        ProblemDetails problemDetails = (ProblemDetails) responseEntity.getBody();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
        assertThat(problemDetails).isNotNull();
        assertThat(problemDetails.getStatus()).isEqualTo(HttpStatus.CONFLICT.value());
        assertThat(problemDetails.getTitle()).contains("CPF already exists");
    }

    @Test
    @DisplayName("handleEmailAlreadyExistsException should return 409 Conflict")
    void handleEmailAlreadyExistsException_shouldReturnConflict() {
        var exception = new EmailAlreadyExistsException("teste@email.com");
        ResponseEntity<Object> responseEntity = restExceptionHandler.handleEmailAlreadyExistsException(exception, webRequestMock);
        ProblemDetails problemDetails = (ProblemDetails) responseEntity.getBody();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
        assertThat(problemDetails).isNotNull();
        assertThat(problemDetails.getStatus()).isEqualTo(HttpStatus.CONFLICT.value());
        assertThat(problemDetails.getTitle()).contains("Email already exists");
    }

    @Test
    @DisplayName("handleCustomerNotFoundException should return 404 Not Found")
    void handleCustomerNotFoundException_shouldReturnNotFound() {
        var exception = new CustomerNotFoundException(UUID.randomUUID());
        ResponseEntity<Object> responseEntity = restExceptionHandler.handleCustomerNotFoundException(exception, webRequestMock);
        ProblemDetails problemDetails = (ProblemDetails) responseEntity.getBody();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(problemDetails).isNotNull();
        assertThat(problemDetails.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @DisplayName("handleUserNotFoundException should return 404 Not Found")
    void handleUserNotFoundException_shouldReturnNotFound() {
        var exception = new UserNotFoundException("teste@email.com");
        ResponseEntity<Object> responseEntity = restExceptionHandler.handleUserNotFoundException(exception, webRequestMock);
        ProblemDetails problemDetails = (ProblemDetails) responseEntity.getBody();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(problemDetails).isNotNull();
        assertThat(problemDetails.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @DisplayName("handleWrongCredentialsException should return 401 Unauthorized")
    void handleWrongCredentialsException_shouldReturnUnauthorized() {
        var exception = new WrongCredentialsException();
        ResponseEntity<Object> responseEntity = restExceptionHandler.handleWrongCredentialsException(exception, webRequestMock);
        ProblemDetails problemDetails = (ProblemDetails) responseEntity.getBody();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(problemDetails).isNotNull();
        assertThat(problemDetails.getStatus()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    @DisplayName("handleInvalidBirthDateException should return 400 Bad Request")
    void handleInvalidBirthDateException_shouldReturnBadRequest() {
        var exception = new InvalidBirthDateException(LocalDate.now().plusDays(1));
        ResponseEntity<Object> responseEntity = restExceptionHandler.handleInvalidBirthDateException(exception, webRequestMock);
        ProblemDetails problemDetails = (ProblemDetails) responseEntity.getBody();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(problemDetails).isNotNull();
        assertThat(problemDetails.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(problemDetails.getTitle()).contains("Invalid Birth Date");
    }

    @Test
    @DisplayName("handleInvalidCPFException should return 400 Bad Request")
    void handleInvalidCPFException_shouldReturnBadRequest() {
        var exception = new InvalidCPFException("123");
        ResponseEntity<Object> responseEntity = restExceptionHandler.handleInvalidCPFException(exception, webRequestMock);
        ProblemDetails problemDetails = (ProblemDetails) responseEntity.getBody();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(problemDetails).isNotNull();
        assertThat(problemDetails.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(problemDetails.getTitle()).contains("Invalid CPF");
    }

    @Test
    @DisplayName("handleMethodArgumentNotValid should return 400 Bad Request with field errors")
    void handleMethodArgumentNotValid_shouldReturnBadRequestWithFieldErrors() {

        BindingResult bindingResultMock = mock(BindingResult.class);
        FieldError fieldError = new FieldError("objectName", "email", "must be a well-formed email address");
        given(bindingResultMock.getFieldErrors()).willReturn(List.of(fieldError));
        MethodArgumentNotValidException exception = new MethodArgumentNotValidException(mock(MethodParameter.class), bindingResultMock);

        ResponseEntity<Object> responseEntity = restExceptionHandler.handleMethodArgumentNotValid(
                exception, new HttpHeaders(), HttpStatus.BAD_REQUEST, webRequestMock);
        ValidationProblemDetails validationDetails = (ValidationProblemDetails) responseEntity.getBody();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(validationDetails).isNotNull();
        assertThat(validationDetails.getFields()).isEqualTo("email");
        assertThat(validationDetails.getFieldsMessage()).isEqualTo("must be a well-formed email address");
    }

    @Test
    @DisplayName("handleInvalidEmailException should return 400 Bad Request")
    void handleInvalidEmailException_shouldReturnBadRequest() {

        var exception = new InvalidEmailException("teste.email");

        ResponseEntity<Object> responseEntity = restExceptionHandler.handleInvalidEmailException(exception, webRequestMock);
        ProblemDetails problemDetails = (ProblemDetails) responseEntity.getBody();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(problemDetails).isNotNull();
        assertThat(problemDetails.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(problemDetails.getTitle()).contains("Invalid Email");
    }

    @Test
    @DisplayName("handleInvalidNameException should return 400 Bad Request")
    void handleInvalidNameException_shouldReturnBadRequest() {

        var exception = new InvalidNameException("");

        ResponseEntity<Object> responseEntity = restExceptionHandler.handleInvalidNameException(exception, webRequestMock);
        ProblemDetails problemDetails = (ProblemDetails) responseEntity.getBody();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(problemDetails).isNotNull();
        assertThat(problemDetails.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(problemDetails.getTitle()).contains("Invalid Name");
    }

    @Test
    @DisplayName("handleInvalidPhoneException should return 400 Bad Request")
    void handleInvalidPhoneException_shouldReturnBadRequest() {

        var exception = new InvalidPhoneException("4115");

        ResponseEntity<Object> responseEntity = restExceptionHandler.handleInvalidPhoneException(exception, webRequestMock);
        ProblemDetails problemDetails = (ProblemDetails) responseEntity.getBody();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(problemDetails).isNotNull();
        assertThat(problemDetails.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(problemDetails.getTitle()).contains("Invalid Phone");
    }
}