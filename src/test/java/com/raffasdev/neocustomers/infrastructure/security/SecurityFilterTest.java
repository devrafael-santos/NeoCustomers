package com.raffasdev.neocustomers.infrastructure.security;

import com.raffasdev.neocustomers.domain.model.user.valueObject.Role;
import com.raffasdev.neocustomers.infrastructure.persistence.jpa.entity.UserEntity;
import com.raffasdev.neocustomers.infrastructure.persistence.jpa.repository.user.IUserJpaRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SecurityFilterTest {

    @Mock
    private TokenService tokenServiceMock;

    @Mock
    private IUserJpaRepository userRepositoryMock;

    @Mock
    private HttpServletRequest requestMock;

    @Mock
    private HttpServletResponse responseMock;

    @Mock
    private FilterChain filterChainMock;

    @InjectMocks
    private SecurityFilter securityFilter;

    @Test
    @DisplayName("doFilterInternal should set Authentication in SecurityContext when token is valid")
    void doFilterInternal_SetsAuthentication_whenTokenIsValid() throws ServletException, IOException {

        String token = "token";
        String userEmail = "teste@email.com";
        UserEntity userEntity = UserEntity.create(
                UUID.randomUUID(),
                "username",
                "teste@email.com",
                Set.of(Role.USER_ADMIN),
                "encodedPassword123"
        );

        given(requestMock.getHeader("Authorization")).willReturn("Bearer " + token);

        given(assertDoesNotThrow(
                () -> tokenServiceMock.validateToken(token))
        ).willReturn(userEmail);

        given(userRepositoryMock.findByEmail(userEmail)).willReturn(Optional.of(userEntity));

        securityFilter.doFilterInternal(requestMock, responseMock, filterChainMock);

        var authentication = SecurityContextHolder.getContext().getAuthentication();
        assertThat(authentication).isNotNull();
        assertThat(authentication.getPrincipal()).isEqualTo(userEntity);

        verify(filterChainMock).doFilter(requestMock, responseMock);

    }

    @Test
    @DisplayName("doFilterInternal should not set Authentication when token is invalid")
    void doFilterInternal_DoesNotSetAuthentication_whenTokenIsInvalid() throws ServletException, IOException {

        String token = "invalidToken";
        given(requestMock.getHeader("Authorization")).willReturn("Bearer " + token);

        given(assertDoesNotThrow(
                () -> tokenServiceMock.validateToken(token))
        ).willReturn(null);

        securityFilter.doFilterInternal(requestMock, responseMock, filterChainMock);

        var authentication = SecurityContextHolder.getContext().getAuthentication();
        assertThat(authentication).isNull();

        verify(filterChainMock).doFilter(requestMock, responseMock);
    }

}