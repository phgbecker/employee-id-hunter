package com.phgbecker.employeeidhunter.schedule.implementation;

import com.phgbecker.employeeidhunter.entity.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class SleepToAvoidDenialOfService implements Consumer<Employee> {
    private static final Logger log = LoggerFactory.getLogger(SleepToAvoidDenialOfService.class);

    @Override
    public void accept(Employee employee) {
        try {
            log.info("Sleeping thread to avoid DoS for a few seconds");

            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            log.error("Oops, an exception happened while sleeping the thread", e);

            Thread.currentThread().interrupt();
        }
    }
}
