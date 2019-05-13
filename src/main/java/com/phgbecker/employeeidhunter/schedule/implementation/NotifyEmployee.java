package com.phgbecker.employeeidhunter.schedule.implementation;

import com.phgbecker.employeeidhunter.entity.Employee;
import com.phgbecker.employeeidhunter.entity.SearchNotification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class NotifyEmployee implements Consumer<Employee> {
    private static final Logger log = LoggerFactory.getLogger(NotifyEmployee.class);
    private final List<SearchNotification> notifications;

    public NotifyEmployee(List<SearchNotification> notifications) {
        this.notifications = notifications;
    }

    @Override
    public void accept(Employee employee) {
        Optional<String> id = employee.getOptionalId();

        if (id.isPresent()) {
            log.info("{} credentials were found ({})", employee, id.get());

            if (notifications != null)
                notifications.forEach(notification -> notification.notifyEmployee(employee));

        } else {
            log.info("{} has no credentials yet", employee);
        }
    }

}
