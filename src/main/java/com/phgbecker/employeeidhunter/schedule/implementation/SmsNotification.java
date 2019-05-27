package com.phgbecker.employeeidhunter.schedule.implementation;

import com.byjg.services.ByJGWebServiceException;
import com.byjg.services.sms.SMSService;
import com.phgbecker.employeeidhunter.entity.Employee;
import com.phgbecker.employeeidhunter.entity.SearchConfiguration;
import com.phgbecker.employeeidhunter.entity.SearchNotification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Optional;

public class SmsNotification implements SearchNotification {
    private static final Logger log = LoggerFactory.getLogger(NotifyEmployee.class);
    private final SearchConfiguration searchConfiguration;

    public SmsNotification(SearchConfiguration searchConfiguration) {
        this.searchConfiguration = searchConfiguration;
    }

    @Override
    public void notifyEmployee(Employee employee) {
        try {
            Optional<String> mobileDDD = employee.getOptionalMobileDDD();
            Optional<String> mobileNumber = employee.getOptionalMobileNumber();

            if (mobileDDD.isPresent() && !mobileDDD.get().isEmpty() && mobileNumber.isPresent() && !mobileNumber.get().isEmpty()) {

                log.info("Sending SMS notification to employee {} on number ({}) {}", employee, mobileDDD.get(), mobileNumber.get());

                SMSService smsService = new SMSService(
                        searchConfiguration.getSmsServiceUsername(),
                        searchConfiguration.getSmsServicePassword()
                );

                String smsResponse = smsService.enviarSMS(
                        mobileDDD.get(),
                        mobileNumber.get(),
                        String.format(searchConfiguration.getSmsNotificationMessage(), employee.getFirstName(), employee.getId())
                );

                log.info("The SMS notification service response was: {}", smsResponse);
            }
        } catch (IOException | ByJGWebServiceException e) {
            log.error("Oops, something wrong happened while sending an SMS to the employee", e);
        }

    }

}
