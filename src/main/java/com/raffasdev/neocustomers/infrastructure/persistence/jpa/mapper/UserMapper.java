package com.raffasdev.neocustomers.infrastructure.persistence.jpa.mapper;

import com.raffasdev.neocustomers.domain.model.shared.valueObject.Email;
import com.raffasdev.neocustomers.domain.model.shared.valueObject.EntityId;
import com.raffasdev.neocustomers.domain.model.shared.valueObject.Name;
import com.raffasdev.neocustomers.domain.model.user.User;
import com.raffasdev.neocustomers.infrastructure.persistence.jpa.entity.UserEntity;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public final class UserMapper {

    public UserEntity toEntity(User user) {

        return UserEntity.create(
                user.getId().getValue(),
                user.getName(),
                user.getEmail(),
                user.getRoles(),
                user.getEncodedPassword()
        );
    }

    public User toDomain(UserEntity entity) {

        if (entity == null) {
            return null;
        }
        return User.reconstitute(
                EntityId.of(entity.getUserId()),
                Name.of(entity.getName()),
                Email.of(entity.getEmail()),
                entity.getRoles(),
                entity.getPassword()
        );
    }

    public Optional<User> toOptionalDomain(Optional<UserEntity> optionalEntity) {

        if (optionalEntity.isEmpty()) {
            return Optional.empty();
        }

        UserEntity userEntity = optionalEntity.get();
        return Optional.of(this.toDomain(userEntity));
    }
}
