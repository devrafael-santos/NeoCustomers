package com.raffasdev.neocustomers.infrastructure.security;

import com.raffasdev.neocustomers.domain.model.user.valueObject.Role;
import com.raffasdev.neocustomers.infrastructure.persistence.jpa.entity.UserEntity;
import com.raffasdev.neocustomers.infrastructure.persistence.jpa.repository.IUserJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImplTest {

    @Mock
    private IUserJpaRepository userJpaRepositoryMock;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Test
    @DisplayName("loadUserByUsername should return UserDetails when user is found")
    void loadUserByUsername_returnsUserDetails_whenUserIsFound() {

        String userEmail = "teste@email.com";
        UserEntity userEntity = UserEntity.create(
                UUID.randomUUID(),
                "username",
                "teste@email.com",
                Set.of(Role.USER_ADMIN),
                "encodedPassword123"
        );

        given(userJpaRepositoryMock.findByEmail(userEmail)).willReturn(Optional.of(userEntity));

        UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(userEmail);

        verify(userJpaRepositoryMock, times(1)).findByEmail(userEmail);

        assertThat(userDetails).isNotNull();
        assertThat(userDetails.getUsername()).isEqualTo(userEmail);
        assertEquals(1, userDetails.getAuthorities().toArray().length);
    }

    @Test
    @DisplayName("loadUserByUsername should throw UsernameNotFoundException when user is not found")
    void loadUserByUsername_throwsUsernameNotFoundException_whenUserIsNotFound() {

        String userEmail = "notfound@email.com";
        given(userJpaRepositoryMock.findByEmail(userEmail)).willReturn(Optional.empty());

        assertThatThrownBy(() -> userDetailsServiceImpl.loadUserByUsername(userEmail))
                .isInstanceOf(UsernameNotFoundException.class);

        verify(userJpaRepositoryMock, times(1)).findByEmail(userEmail);
    }
}