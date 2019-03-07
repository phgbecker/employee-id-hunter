package com.phgbecker.employeeidhunter.schedule;

import com.phgbecker.employeeidhunter.dao.EmployeeDAO;
import com.phgbecker.employeeidhunter.entity.Employee;
import com.phgbecker.employeeidhunter.implementation.EmployeeWithoutId;
import com.phgbecker.employeeidhunter.implementation.NotifyEmployee;
import com.phgbecker.employeeidhunter.implementation.SearchEmployeeId;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class EmployeeIdSearchSchedule {
    private EmployeeDAO employeeDAO;
    private List<Employee> employees;

    public EmployeeIdSearchSchedule() throws IOException {
        employeeDAO = new EmployeeDAO();
        employees = employeeDAO.getEmployees();
    }

    @Scheduled(fixedDelay = 1800000)
    public void search() {
        employees.stream()
                .filter(new EmployeeWithoutId())
                .forEach(new SearchEmployeeId().andThen(new NotifyEmployee()));

        employeeDAO.saveToFile(employees);
    }

}
