package com.raffasdev.neocustomers.domain.model.shared.valueObject;

import java.util.Objects;

public abstract class Entity<ID extends ValueObject> {

    private final ID id;

    protected Entity(ID id) {
        this.id = id;
    }

    public ID getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Entity<?> entity = (Entity<?>) o;
        return Objects.equals(id, entity.id);
    }

    @Override
    public int hashCode() {
        return 31 * (id != null ? id.hashCode() : 0);
    }
}
