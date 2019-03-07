package com.phgbecker.employeeidhunter.entity;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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

	@Override
	public String toString() {
		return (firstName + " " + lastName).toUpperCase();
	}

	public Optional<String> getId() {
		return Optional.ofNullable(id);
	}

	public Optional<String> getMobileDDD() {
		return Optional.ofNullable(mobileDDD);
	}

	public Optional<String> getMobileNumber() {
		return Optional.ofNullable(mobileNumber);
	}

}
