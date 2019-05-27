package com.phgbecker.employeeidhunter.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

import java.util.Objects;
import java.util.Optional;

@Getter
public class Employee {
    private String id;
    private String fullName;
    private String email;
    private String mobileDDD;
    private String mobileNumber;

    public Employee() {
    }

    public Employee(String fullName) {
        this.fullName = fullName;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return fullName == null ? super.toString() : fullName.toUpperCase();
    }

    @JsonIgnore
    public String getFirstName() {
        return isFullName() ? fullName.substring(0, fullName.indexOf(' ')) : fullName;
    }

    @JsonIgnore
    public String getLastName() {
        if (!isFullName()) {
            throw new IndexOutOfBoundsException(fullName + " has no last names");
        }

        return fullName.substring(fullName.lastIndexOf(' ') + 1);
    }

    @JsonIgnore
    public String getNameOrderedBySurname() {
        if (isFullName()) {
            String firstName = fullName.substring(0, fullName.indexOf(' '));
            String lastName = fullName.substring(fullName.lastIndexOf(' ') + 1);

            return lastName + ", " + firstName;
        }

        return fullName.trim();
    }

    private boolean isFullName() {
        Objects.requireNonNull(fullName, "The attribute \"fullName\" hasn't been provided");

        return fullName.trim().split(" ").length > 1;
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
