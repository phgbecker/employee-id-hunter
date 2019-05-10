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
import java.io.IOException;
import java.util.List;

@Component
public class EmployeeDAO {
    private static final Logger log = LoggerFactory.getLogger(EmployeeDAO.class);
    private final ObjectMapper objectMapper;
    private final ObjectWriter objectWriter;

    @Value("${employees.file}")
    private String employeesFile;

    public EmployeeDAO() {
        objectMapper = new ObjectMapper();
        objectWriter = objectMapper.writer(new DefaultPrettyPrinter());
    }

    /**
     * Return a list of Employee from a JSON file
     *
     * @return Employees
     * @throws IOException File not found, or JSON mapping problems
     */
    public List<Employee> getEmployees() throws IOException {
        log.info("Reading employees from file: {}", employeesFile);

        List<Employee> employees = objectMapper.readValue(
                new File(employeesFile),
                objectMapper.getTypeFactory().constructCollectionType(List.class, Employee.class)
        );

        log.info("Read {} employee(s) from file", employees.size());

        return employees;
    }

    /**
     * Save a List<Employee> to a JSON file
     *
     * @param employees List<Employee>
     */
    public void save(List<Employee> employees) {
        try {
            log.info("Saving employees to file: {}", employeesFile);

            objectWriter.writeValue(
                    new File(employeesFile),
                    employees
            );
        } catch (IOException e) {
            log.error("Oops, something wrong happened while saving the resources", e);
        }
    }
}
