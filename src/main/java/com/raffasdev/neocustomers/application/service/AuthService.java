package com.raffasdev.neocustomers.application.service;

import com.raffasdev.neocustomers.application.exception.EmailAlreadyExistsException;
import com.raffasdev.neocustomers.application.exception.UserNotFoundException;
import com.raffasdev.neocustomers.application.exception.WrongCredentialsException;
import com.raffasdev.neocustomers.domain.model.shared.valueObject.Email;
import com.raffasdev.neocustomers.domain.model.shared.valueObject.EntityId;
import com.raffasdev.neocustomers.domain.model.shared.valueObject.Name;
import com.raffasdev.neocustomers.domain.model.user.IAuthService;
import com.raffasdev.neocustomers.domain.model.user.User;
import com.raffasdev.neocustomers.domain.model.user.valueObject.Role;
import com.raffasdev.neocustomers.infrastructure.persistence.jpa.repository.UserRepository;
import com.raffasdev.neocustomers.infrastructure.security.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@AllArgsConstructor
public class AuthService implements IAuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final TokenService tokenService;

    @Transactional
    @Override
    public String loginUser(String email, String password) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));

        if (!passwordEncoder.matches(password, user.getEncodedPassword())) {
            throw new WrongCredentialsException();
        }

        return tokenService.generateToken(user.getEmail());
    }

    @Transactional
    @Override
    public User registerUser(String username, String email, Set<Role> roles, String password) {
        if (userRepository.existsByEmail(email)) {
            throw new EmailAlreadyExistsException(email);
        }

        String encodedPassword = passwordEncoder.encode(password);

        EntityId entityId = EntityId.newId();

        User user = User.create(
                entityId,
                Name.newUsername(username),
                Email.newEmail(email),
                roles,
                encodedPassword
        );

        return userRepository.save(user);
    }

}