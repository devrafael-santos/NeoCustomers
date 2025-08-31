package com.raffasdev.neocustomers.domain.model.customer;

import com.raffasdev.neocustomers.domain.model.customer.valueObject.CPF;
import com.raffasdev.neocustomers.domain.model.customer.valueObject.Phone;
import com.raffasdev.neocustomers.domain.model.shared.valueObject.Email;
import com.raffasdev.neocustomers.domain.model.shared.valueObject.Entity;
import com.raffasdev.neocustomers.domain.model.shared.valueObject.EntityId;
import com.raffasdev.neocustomers.domain.model.shared.valueObject.Name;

public class Customer extends Entity<EntityId> {

    private final Name name;
    private final Email email;
    private final CPF cpf;
    private final Phone phone;

    private Customer(EntityId id, Name name, Email email, CPF cpf, Phone phone) {
        super(id);
        this.name = name;
        this.email = email;
        this.cpf = cpf;
        this.phone = phone;
    }

    public static Customer create(EntityId id, Name name, Email email, CPF cpf, Phone phone) {
        return new Customer(id, name, email, cpf, phone);
    }

    public static Customer reconstitute(EntityId id, Name name, Email email, CPF cpf, Phone phone) {
        return new Customer(id, name, email, cpf, phone);
    }

    public boolean hasName(Name name) {
        return this.name.equals(name);
    }

    public boolean hasEmail(Email email) {
        return this.email.equals(email);
    }

    public boolean hasCPF(CPF cpf) {
        return this.cpf.equals(cpf);
    }

    public boolean hasPhone(Phone phone) {
        return this.phone.equals(phone);
    }

    public String getName() {
        return this.name.getValue();
    }

    public String getEmail() {
        return this.email.getValue();
    }

    public String getCPF() {
        return this.cpf.getValue();
    }

    public String getPhone() {
        return this.phone.getValue();
    }

}
