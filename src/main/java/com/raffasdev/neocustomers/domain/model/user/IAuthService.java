package com.raffasdev.neocustomers.domain.model.user;

import com.raffasdev.neocustomers.domain.model.user.valueObject.Role;

import java.util.Set;

public interface IAuthService {

    String loginUser(String email, String password);

    User registerUser(String username, String email, Set<Role> roles, String password);
}
