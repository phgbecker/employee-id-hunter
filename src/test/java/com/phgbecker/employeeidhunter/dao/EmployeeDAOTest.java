package com.phgbecker.employeeidhunter.dao;

import com.phgbecker.employeeidhunter.entity.Employee;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

public class EmployeeDAOTest {
    private String employeesTestFile;
    private EmployeeDAO employeeDAO;

    @Before
    public void setUp() throws IOException {
        employeesTestFile = "employeesTestFile.json";
        String employeesTestFileContents = "[{\"fullName\":\"John Doe\"}]";

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(employeesTestFile))) {
            bufferedWriter.write(employeesTestFileContents);
        }

        employeeDAO = new EmployeeDAO(employeesTestFile);
    }

    @After
    public void tearDown() throws Exception {
        Files.delete(Paths.get(employeesTestFile));
    }

    @Test
    public void givenAnEmployeesFile_whenGetEmployees_thenHasAtLeastOneEmployee() {
        assertThat(
                employeeDAO.getEmployees()
        ).hasAtLeastOneElementOfType(Employee.class);
    }

    @Test
    public void givenAnEmployeesList_whenSave_thenContainsMatch() throws IOException {
        employeeDAO.save(Collections.singletonList(new Employee("John Doe")));

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(employeesTestFile))) {
            assertThat(
                    bufferedReader.lines().anyMatch(line -> line.contains("John Doe"))
            ).isTrue();
        }
    }

}