package ua.org.goservice.cashdesk.controller.auth;

import ua.org.goservice.cashdesk.model.employee.Employee;

public interface AuthCaller {
    void signIn();
    void lockScreen();
    Employee getEmployee();
}
