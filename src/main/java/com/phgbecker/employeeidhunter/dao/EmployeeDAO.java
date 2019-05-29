package com.phgbecker.employeeidhunter.dao;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.phgbecker.employeeidhunter.entity.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Component
public class EmployeeDAO {
    private static final Logger log = LoggerFactory.getLogger(EmployeeDAO.class);
    private final ObjectMapper objectMapper;
    private final ObjectWriter objectWriter;

    private String employeesFile;

    public EmployeeDAO(@Value("${employees.file}") String employeesFile) {
        objectMapper = new ObjectMapper();
        objectWriter = objectMapper.writer(new DefaultPrettyPrinter());

        this.employeesFile = employeesFile;
    }

    /**
     * Return a list of Employee from a JSON file
     *
     * @return Employees
     */
    public List<Employee> getEmployees() {
        log.info("Reading employees from file: {}", employeesFile);

        List<Employee> employees = Collections.emptyList();
        try {
            employees = objectMapper.readValue(
                    new File(employeesFile),
                    objectMapper.getTypeFactory().constructCollectionType(List.class, Employee.class)
            );

            log.info("Read {} employee(s) from file", employees.size());
        } catch (FileNotFoundException e) {
            log.error("Oops, the employees file wasn't found: {}", e.getMessage());
        } catch (IOException e) {
            log.error("Oops, something wrong happened while reading the employees file", e);
        }

        return employees;
    }

    /**
     * Save a List<Employee> to a JSON file
     *
     * @param employees List<Employee>
     */
    public void save(List<Employee> employees) {
        try {
            if (!employees.isEmpty()) {
                log.info("Saving employees to file: {}", employeesFile);

                objectWriter.writeValue(
                        new File(employeesFile),
                        employees
                );
            }
        } catch (IOException e) {
            log.error("Oops, something wrong happened while saving the resources", e);
        }
    }
}
