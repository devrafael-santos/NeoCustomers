package com.raffasdev.neocustomers.domain.model.shared.valueObject;

import java.util.Objects;
import java.util.UUID;

public class EntityId extends ValueObject {

    private final UUID id;

    private EntityId(UUID id) {
        this.id = id;
        validate();
    }

    public static EntityId of(UUID id) {
        return new EntityId(id);
    }

    public static EntityId newId() {
        return new EntityId(UUID.randomUUID());
    }

    public UUID getValue() {
        return this.id;
    }

    private void validate() {
        if (id == null) {
            throw new IllegalArgumentException("Entity ID cannot be null");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        EntityId entityId = (EntityId) o;
        return Objects.equals(id, entityId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return getValue().toString();
    }
}
