package com.phgbecker.employeeidhunter.entity;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class EmployeeTest {

    private static Employee employeeWithFullName;
    private static Employee employeeWithoutFullName;

    @BeforeClass
    public static void setUp() {
        employeeWithFullName = new Employee("John Doe");
        employeeWithoutFullName = new Employee("John");
    }

    @Test
    public void givenEmployeeWithFullName_whenToString_thanEquals() {
        assertThat(employeeWithFullName.toString()).isEqualTo("JOHN DOE");
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
    public void givenEmployeeWithoutFullName__whenGetNameOrderedBySurname_thenEquals() {
        assertThat(employeeWithoutFullName.getNameOrderedBySurname()).isEqualTo("John");
    }

    @Test
    public void givenAnEmployeeWithoutFullName_whenGetLastName_thanIsInstanceOfIndexOutOfBoundsException() {
        assertThatThrownBy(
                () -> employeeWithoutFullName.getLastName()
        ).isInstanceOf(
                IndexOutOfBoundsException.class
        );
    }

    @Test
    public void givenEmployeeWithNullOptionalAttributes_whenGetOptionalAttributes_thenIsNotPresent() {
        Employee employeeWithNullOptionalAttributes = new Employee();

        assertThat(employeeWithNullOptionalAttributes.getOptionalId()).isNotPresent();
        assertThat(employeeWithNullOptionalAttributes.getOptionalMobileDDD()).isNotPresent();
        assertThat(employeeWithNullOptionalAttributes.getOptionalMobileNumber()).isNotPresent();
    }

}