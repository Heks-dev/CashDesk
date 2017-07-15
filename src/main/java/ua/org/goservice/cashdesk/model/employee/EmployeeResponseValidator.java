package ua.org.goservice.cashdesk.model.employee;

import ua.org.goservice.cashdesk.model.exception.AuthorizationFailedException;
import ua.org.goservice.cashdesk.model.exception.Exceptions;
import ua.org.goservice.cashdesk.model.util.Validator;

class EmployeeResponseValidator implements Validator<String> {

    private static final String LOGIN_FAILED_CODE = "E001";

    @Override
    public void validate(String val) {
        if (val.startsWith(LOGIN_FAILED_CODE)) {
            throw new AuthorizationFailedException(Exceptions.WRONG_PASSWORD);
        }
    }
}
