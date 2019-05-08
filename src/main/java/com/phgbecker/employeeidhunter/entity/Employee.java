package com.phgbecker.employeeidhunter.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

import java.util.Optional;

@Getter
public class Employee {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String mobileDDD;
    private String mobileNumber;

    public Employee() {
    }

    public Employee(String firstName, String lastName, String email, String mobileDDD, String mobileNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.mobileDDD = mobileDDD;
        this.mobileNumber = mobileNumber;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return (firstName + " " + lastName).toUpperCase();
    }

    @JsonIgnore
    public Optional<String> getOptionalId() {
        return Optional.ofNullable(id);
    }

    @JsonIgnore
    public Optional<String> getOptionalMobileDDD() {
        return Optional.ofNullable(mobileDDD);
    }

    @JsonIgnore
    public Optional<String> getOptionalMobileNumber() {
        return Optional.ofNullable(mobileNumber);
    }

}
