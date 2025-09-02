package com.raffasdev.neocustomers.domain.model.shared.valueObject;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EntityIdTest {

    @Test
    @DisplayName("newId should create EntityId with a valid UUID")
    void newId_CreatesEntityIdWithValidUUID() {

        EntityId entityId = EntityId.newId();

        assertNotNull(entityId);
        assertNotNull(entityId.getValue());
        assertFalse(entityId.getValue().toString().isEmpty());
    }

    @Test
    @DisplayName("of should create EntityId with valid UUID")
    void of_CreatesEntityIdWithProvidedUUID() {

        var expectedUuid = java.util.UUID.randomUUID();
        EntityId entityId = EntityId.of(expectedUuid);

        assertNotNull(entityId);
        assertEquals(expectedUuid, entityId.getValue());
    }

    @Test
    @DisplayName("of should throw IllegalArgumentException when null UUID is provided")
    void of_ThrowsIllegalArgumentException_WhenNullUUIDIsProvided() {
        assertThrows(IllegalArgumentException.class, () -> EntityId.of(null));
    }

    @Test
    @DisplayName("equals should return true when EntityIds are equals")
    void equals_ReturnsTrue_WhenEntityIdsAreEqual() {

        EntityId id1 = EntityId.newId();
        EntityId id2 = EntityId.of(id1.getValue());

        assertEquals(id1, id2);
        assertEquals(id1.hashCode(), id2.hashCode());
    }

    @Test
    @DisplayName("equals should return false when EntityIds are not equals")
    void equals_ReturnsFalse_WhenEntityIdsAreNotEqual() {

        EntityId id1 = EntityId.newId();
        EntityId id2 = EntityId.newId();

        assertNotEquals(id1, id2);
        assertNotEquals(id1.hashCode(), id2.hashCode());
    }

    @Test
    @DisplayName("toString should return the UUID as a string")
    void toString_ReturnsUUIDAsString() {

        EntityId entityId = EntityId.newId();
        String expectedString = entityId.getValue().toString();

        assertEquals(expectedString, entityId.toString());
    }

    @Test
    @DisplayName("getValue should return the UUID value")
    void getValue_ReturnsUUIDValue() {

        EntityId entityId = EntityId.newId();
        java.util.UUID expectedUuid = entityId.getValue();

        assertNotNull(expectedUuid);
        assertEquals(expectedUuid, entityId.getValue());
    }

}