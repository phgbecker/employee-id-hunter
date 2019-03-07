package com.phgbecker.employeeidhunter.dao;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.phgbecker.employeeidhunter.entity.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class EmployeeDAO {
    private static Logger log = LoggerFactory.getLogger(EmployeeDAO.class);
    private static final String EMPLOYEES_FILE = "employees.json";
    private ObjectMapper objectMapper;
    private ObjectWriter objectWriter;

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
        log.info("Reading employees from file: {}", EMPLOYEES_FILE);

        return objectMapper.readValue(
                new File(EMPLOYEES_FILE),
                objectMapper.getTypeFactory().constructCollectionType(List.class, Employee.class)
        );
    }

    /**
     * Save a List<Employee> to a JSON file
     *
     * @param employees List<Employee>
     */
    public void saveToFile(List<Employee> employees) {
        try {
            log.info("Saving {} employee(s) to file: {}",employees.size(),  EMPLOYEES_FILE);

            objectWriter.writeValue(
                    new File(EMPLOYEES_FILE),
                    employees
            );
        } catch (IOException e) {
            log.error("Oops, something wrong happened while saving the resources", e);
        }
    }
}