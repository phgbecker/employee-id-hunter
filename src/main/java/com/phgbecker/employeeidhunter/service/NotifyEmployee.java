package com.phgbecker.employeeidhunter.service;

import java.io.IOException;
import java.util.Optional;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.byjg.services.ByJGWebServiceException;
import com.byjg.services.sms.SMSService;
import com.phgbecker.employeeidhunter.entity.Employee;

public class NotifyEmployee implements Consumer<Employee> {
	private static Logger log = LoggerFactory.getLogger(NotifyEmployee.class);
	private static SMSService smsService = new SMSService("", "");
	private static final String DEFAULT_MESSAGE = "Dear %s, take note, your credential has arrived: %s (by eID Hunter)";

	@Override
	public void accept(Employee employee) {
		Optional<String> id = employee.getId();

		if (id.isPresent()) {
			log.info("{} credentials were found ({})", employee, id.get());

			try {
				Optional<String> mobileDDD = employee.getMobileDDD();
				Optional<String> mobileNumber = employee.getMobileNumber();

				if (mobileDDD.isPresent() && !mobileDDD.get().isEmpty() && mobileNumber.isPresent() && !mobileNumber.get().isEmpty()) {
					smsService.enviarSMS(
							mobileDDD.get(),
							mobileNumber.get(),
							String.format(DEFAULT_MESSAGE, employee.getFirstName(), id.get())
					);
				}
			} catch (IOException | ByJGWebServiceException e) {
				log.error("Oops, something wrong happened while notifying the employee", e);
			}
		} else {
			log.info("{} has no credentials yet", employee);
		}
	}

}
