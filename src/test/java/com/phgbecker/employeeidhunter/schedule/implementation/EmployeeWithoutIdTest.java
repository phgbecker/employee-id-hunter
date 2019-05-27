package com.phgbecker.employeeidhunter.schedule.implementation;

import com.phgbecker.employeeidhunter.entity.Employee;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

public class EmployeeWithoutIdTest {

    private Employee employee;

    @Before
    public void setUp() {
        employee = new Employee();
    }

    @Test
    public void givenAnEmployeeWithId_whenApplyingEmployeeWithoutIdPredicate_thenFalse() {
        employee.setId("eID");

        Assertions.assertThat(
                new EmployeeWithoutId().test(employee)
        ).isFalse();
    }

    @Test
    public void givenAnEmployeeWithoutId_whenApplyingEmployeeWithoutIdPredicate_thenTrue() {
        employee.setId(null);

        Assertions.assertThat(
                new EmployeeWithoutId().test(employee)
        ).isTrue();
    }

}