package com.phgbecker.employeeidhunter.entity;

@FunctionalInterface
public interface SearchNotification {

    void notifyEmployee(Employee employee);
}
