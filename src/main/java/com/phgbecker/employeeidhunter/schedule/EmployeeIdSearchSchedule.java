package com.phgbecker.employeeidhunter.schedule;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.phgbecker.employeeidhunter.entity.Employee;
import com.phgbecker.employeeidhunter.service.EmployeeWithoutId;
import com.phgbecker.employeeidhunter.service.NotifyEmployee;
import com.phgbecker.employeeidhunter.service.SearchEmployeeId;

@Component
public class EmployeeIdSearchSchedule {
	private static final String EMPLOYEES_FILE = "employees.json";
	private List<Employee> employees;

	public EmployeeIdSearchSchedule() throws IOException {
		employees = getListOfEmployeesWithoutId();
	}

	/**
	 * Parse the EMPLOYEES_FILE file elements to a Collection of Employee
	 *
	 * @return
	 * @throws IOException
	 */
	private List<Employee> getListOfEmployeesWithoutId() throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();

		return objectMapper.readValue(
					new File(EMPLOYEES_FILE),
					objectMapper.getTypeFactory().constructCollectionType(List.class, Employee.class)
				);
	}

	/**
	 * Task that hunts for employee IDs
	 */
	@Scheduled(fixedDelay = 3600000)
	public void search() {
		employees.stream()
				.filter(new EmployeeWithoutId())
				.forEach(new SearchEmployeeId().andThen(new NotifyEmployee())
		);
	}
}
