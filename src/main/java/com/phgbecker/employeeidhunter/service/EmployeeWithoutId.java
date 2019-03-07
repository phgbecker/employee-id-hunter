package com.phgbecker.employeeidhunter.service;

import java.util.function.Predicate;

import com.phgbecker.employeeidhunter.entity.Employee;

public class EmployeeWithoutId implements Predicate<Employee> {

	@Override
	public boolean test(Employee employee) {
		return !employee.getId().isPresent();
	}

}
