package com.raffasdev.neocustomers.domain.model.customer;

import com.raffasdev.neocustomers.domain.model.customer.valueObject.BirthDate;
import com.raffasdev.neocustomers.domain.model.customer.valueObject.CPF;
import com.raffasdev.neocustomers.domain.model.customer.valueObject.Phone;
import com.raffasdev.neocustomers.domain.model.shared.valueObject.Email;
import com.raffasdev.neocustomers.domain.model.shared.valueObject.Entity;
import com.raffasdev.neocustomers.domain.model.shared.valueObject.EntityId;
import com.raffasdev.neocustomers.domain.model.shared.valueObject.Name;

import java.time.LocalDate;

public class Customer extends Entity<EntityId> {

    private final Name name;
    private final Email email;
    private final CPF cpf;
    private final Phone phone;
    private final BirthDate birthDate;

    private Customer(EntityId id, Name name, Email email, CPF cpf, Phone phone, BirthDate birthDate) {
        super(id);
        this.name = name;
        this.email = email;
        this.cpf = cpf;
        this.phone = phone;
        this.birthDate = birthDate;
    }

    public static Customer create(EntityId id, Name name, Email email, CPF cpf, Phone phone, BirthDate birthDate) {
        return new Customer(id, name, email, cpf, phone, birthDate);
    }

    public static Customer reconstitute(EntityId id, Name name, Email email, CPF cpf, Phone phone, BirthDate birthDate) {
        return new Customer(id, name, email, cpf, phone, birthDate);
    }

    public int getAge() {

        return this.birthDate.getAge();
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

    public  boolean hasBirthDate(BirthDate birthDate) { return  this.birthDate.equals(birthDate); }

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

    public LocalDate getBirthDate() { return this.birthDate.getValue(); }

}
