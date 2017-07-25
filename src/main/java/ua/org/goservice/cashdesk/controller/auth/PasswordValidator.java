package ua.org.goservice.cashdesk.controller.auth;

import ua.org.goservice.cashdesk.model.exception.AuthorizationFailedException;
import ua.org.goservice.cashdesk.model.exception.Exceptions;
import ua.org.goservice.cashdesk.model.util.Validator;

public class PasswordValidator implements Validator<String> {

    private static final String CONVENTION_EXP = "[^\\W_]{3,16}";

    @Override
    public void validate(String password) {
        if (!password.matches(CONVENTION_EXP)) {
            throw new AuthorizationFailedException(Exceptions.WRONG_PASSWORD_FORMAT);
        }
    }
}
