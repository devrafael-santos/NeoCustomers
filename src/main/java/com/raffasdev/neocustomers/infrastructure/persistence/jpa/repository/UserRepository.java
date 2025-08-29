package com.raffasdev.neocustomers.infrastructure.persistence.jpa.repository;

import com.raffasdev.neocustomers.domain.model.user.IUserRepository;
import com.raffasdev.neocustomers.domain.model.user.User;
import com.raffasdev.neocustomers.infrastructure.persistence.jpa.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepository implements IUserRepository {

    private final IUserJpaRepository jpaRepository;

    private final UserMapper userMapper;

    @Override
    public User save(User user) {
        return userMapper.toDomain(jpaRepository.save(userMapper.toEntity(user)));
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaRepository.existsByEmail(email);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userMapper.toOptionalDomain(jpaRepository.findByEmail(email));
    }

    @Override
    public boolean existsByUsername(String username) {
        return jpaRepository.existsByUsername(username);
    }
}