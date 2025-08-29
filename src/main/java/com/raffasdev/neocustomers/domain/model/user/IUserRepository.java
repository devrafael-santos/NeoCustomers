package com.raffasdev.neocustomers.domain.model.user;

import java.util.Optional;

public interface IUserRepository {

    User save(User user);

    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    boolean existsByUsername(String username);
}
