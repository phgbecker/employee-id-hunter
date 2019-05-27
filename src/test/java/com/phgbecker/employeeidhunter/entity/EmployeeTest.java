package com.phgbecker.employeeidhunter.entity;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class EmployeeTest {

    private Employee employeeWithFullName;
    private Employee employeeWithoutFullName;

    @Before
    public void setUp() {
        employeeWithFullName = new Employee("John Doe");
        employeeWithoutFullName = new Employee("John");
    }

    @Test
    public void givenAnEmployeeWithFullName_whenGetFirstName_thanEquals() {
        assertThat(employeeWithFullName.getFirstName()).isEqualTo("John");
    }

    @Test
    public void givenAnEmployeeWithFullName_whenGetLastName_thanEquals() {
        assertThat(employeeWithFullName.getLastName()).isEqualTo("Doe");
    }

    @Test
    public void givenAnEmployeeWithFullName_whenGetNameOrderedBySurname_thenEquals() {
        assertThat(employeeWithFullName.getNameOrderedBySurname()).isEqualTo("Doe, John");
    }

    @Test
    public void givenAnEmployeeWithoutFullName_whenGetLastName_thanIsInstanceOfIndexOutOfBoundsException() {
        assertThatThrownBy(
                () -> employeeWithoutFullName.getLastName()
        ).isInstanceOf(
                IndexOutOfBoundsException.class
        );
    }

}