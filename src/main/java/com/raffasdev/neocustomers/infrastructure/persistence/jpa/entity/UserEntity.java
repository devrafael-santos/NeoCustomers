package com.raffasdev.neocustomers.infrastructure.persistence.jpa.entity;

import com.raffasdev.neocustomers.domain.model.user.valueObject.Role;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
@Getter
public class UserEntity implements UserDetails {

    @Id
    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "username", nullable = false)
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role", nullable = false)
    private Set<Role> roles = new HashSet<>();

    @Column(nullable = false)
    private String password;

    public UserEntity() {
    }

    private UserEntity(UUID userId, String username, String email, Set<Role> roles, String password) {
        this.userId = userId;
        this.name = username;
        this.email = email;
        this.roles = roles;
        this.password = password;
    }

    public static UserEntity create(UUID userId, String username, String email, Set<Role> roles, String password) {
        return new UserEntity(userId, username, email, roles, password);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .collect(Collectors.toSet());
    }

    @Override
    public String getUsername() {
        return this.email;
    }
}
