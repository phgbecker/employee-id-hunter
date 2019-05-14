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

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return fullName == null ? super.toString() : fullName.toUpperCase();
    }

    @JsonIgnore
    public String getFirstName() {
        Objects.requireNonNull(fullName, "The attribute fullName hasn't been provided");

        return fullName.substring(0, fullName.indexOf(" "));
    }

    @JsonIgnore
    public String getLastName() {
        Objects.requireNonNull(fullName, "The attribute fullName hasn't been provided");

        return fullName.substring(fullName.lastIndexOf(" "));
    }

    @JsonIgnore
    public String getNameOrderedBySurname() {
        Objects.requireNonNull(fullName, "The attribute fullName hasn't been provided");

        if (fullName.trim().split(" ").length > 1) {
            String firstName = fullName.substring(0, fullName.indexOf(" "));
            String lastName = fullName.substring(fullName.lastIndexOf(" ") + 1);

            return lastName + ", " + firstName;
        }

        return fullName.trim();
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
