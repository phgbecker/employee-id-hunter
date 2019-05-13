package com.phgbecker.employeeidhunter.schedule;

import com.phgbecker.employeeidhunter.dao.EmployeeDAO;
import com.phgbecker.employeeidhunter.entity.Employee;
import com.phgbecker.employeeidhunter.entity.SearchConfiguration;
import com.phgbecker.employeeidhunter.schedule.implementation.EmployeeWithoutId;
import com.phgbecker.employeeidhunter.schedule.implementation.NotifyEmployee;
import com.phgbecker.employeeidhunter.schedule.implementation.SearchEmployeeId;
import com.phgbecker.employeeidhunter.schedule.implementation.SmsNotification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class EmployeeIdSearchSchedule {
    private static final Logger log = LoggerFactory.getLogger(EmployeeIdSearchSchedule.class);
    private final EmployeeDAO employeeDAO;
    private final SearchConfiguration searchConfiguration;
    private final List<Employee> employees;

    @Autowired
    public EmployeeIdSearchSchedule(SearchConfiguration searchConfiguration, EmployeeDAO employeeDAO) {
        this.searchConfiguration = searchConfiguration;
        this.employeeDAO = employeeDAO;
        employees = employeeDAO.getEmployees();
    }

    /**
     * Schedule task that hunts for employee IDs
     */
    @Scheduled(fixedDelayString = "${schedule.search.delay}")
    public void search() {
        employees.stream()
                .filter(new EmployeeWithoutId())
                .forEach(
                        new SearchEmployeeId(searchConfiguration)
                                .andThen(new NotifyEmployee(Collections.singletonList(new SmsNotification(searchConfiguration))))
                );

        employeeDAO.save(employees);

        checkIfSearchShouldStop();
    }

    private void checkIfSearchShouldStop() {
        if (employees.stream().noneMatch(new EmployeeWithoutId())) {
            log.info("No more IDs need to be look up. Shutting down application!");
            System.exit(0);
        }
    }

}
