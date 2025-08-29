package com.raffasdev.neocustomers.domain.model.user;

import com.raffasdev.neocustomers.domain.model.shared.valueObject.Email;
import com.raffasdev.neocustomers.domain.model.shared.valueObject.Entity;
import com.raffasdev.neocustomers.domain.model.shared.valueObject.EntityId;
import com.raffasdev.neocustomers.domain.model.shared.valueObject.Username;
import com.raffasdev.neocustomers.domain.model.user.valueObject.Role;

import java.util.Set;

public class User extends Entity<EntityId> {

    private final Username username;
    private final Email email;
    private final Set<Role> roles;
    private final String encodedPassword;

    private User(EntityId id, Username username, Email email, Set<Role> roles, String encodedPassword) {
        super(id);
        this.username = username;
        this.email = email;
        this.roles = roles;
        this.encodedPassword = encodedPassword;
    }

    public static User create(EntityId id, Username username, Email email, Set<Role> roles, String encodedPassword) {
        return new User(id, username, email, roles, encodedPassword);
    }

    public static User reconstitute(EntityId id, Username username, Email email, Set<Role> roles, String encodedPassword) {
        return new User(id, username, email, roles, encodedPassword);
    }

    public boolean hasUsername(Username username) {
        return this.username.equals(username);
    }

    public boolean hasEmail(Email email) {
        return this.email.equals(email);
    }

    public boolean hasEncodedPassword(String encodedPassword) {
        return this.encodedPassword.equals(encodedPassword);
    }

    public String getUsername() {
        return this.username.getValue();
    }

    public String getEmail() {
        return this.email.getValue();
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public String getEncodedPassword() {
        return this.encodedPassword;
    }
}