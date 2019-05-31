package com.phgbecker.employeeidhunter.schedule.implementation;

import com.phgbecker.employeeidhunter.entity.Employee;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class EmployeeWithoutIdTest {

    private static Employee employee;

    @BeforeClass
    public static void setUp() {
        employee = new Employee();
    }

    @Test
    public void givenAnEmployeeWithId_whenApplyingEmployeeWithoutIdPredicate_thenFalse() {
        employee.setId("eID");

        assertThat(
                new EmployeeWithoutId().test(employee)
        ).isFalse();
    }

    @Test
    public void givenAnEmployeeWithoutId_whenApplyingEmployeeWithoutIdPredicate_thenTrue() {
        employee.setId(null);

        assertThat(
                new EmployeeWithoutId().test(employee)
        ).isTrue();
    }

}