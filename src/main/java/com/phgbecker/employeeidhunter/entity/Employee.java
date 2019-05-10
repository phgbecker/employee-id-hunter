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
