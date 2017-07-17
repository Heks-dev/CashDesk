package ua.org.goservice.cashdesk.controller.auth;

import ua.org.goservice.cashdesk.model.employee.Employee;
import ua.org.goservice.cashdesk.model.exception.AuthorizationFailedException;
import ua.org.goservice.cashdesk.model.exception.Exceptions;
import ua.org.goservice.cashdesk.model.util.Validator;

class AuthValidator implements Validator<String> {
    private static final String PASSWORD_CONVENTION = "[^\\W_]{3,16}";

    private String provenPass;
    private Employee employee;

    @Override
    public void validate(String val) {
        checkConventionCompliance(val);
        boolean actuallyLogged = provenPass != null;
        if (actuallyLogged) {
            compareToProven(val);
        } else {
            loadNewAccount(val);
        }
    }

    private void loadNewAccount(String val) {
        employee = new Employee(val);
        provenPass = val;
    }

    private void checkConventionCompliance(String val) {
        boolean notCompliedConvention = !val.matches(PASSWORD_CONVENTION);
        if (notCompliedConvention) {
            throw new AuthorizationFailedException(Exceptions.WRONG_PASSWORD_FORMAT);
        }
    }

    private void compareToProven(String val) {
        if (!val.equals(provenPass)) {
            throw new AuthorizationFailedException(Exceptions.UNLOCK_FAILED);
        }
    }

    void resetPassword() {
        if (provenPass != null)
            provenPass = null;
    }

    Employee getLoadedEmploy() {
        return employee;
    }
}
