package ua.org.goservice.cashdesk.controller.auth;

import ua.org.goservice.cashdesk.model.communication.Extractive;
import ua.org.goservice.cashdesk.model.exception.AuthorizationFailedException;
import ua.org.goservice.cashdesk.model.exception.Exceptions;
import ua.org.goservice.cashdesk.model.util.Hashing;
import ua.org.goservice.cashdesk.model.util.validator.Validator;

class PasswordValidator implements Validator<String>, Extractive {

    private static final String CONVENTION_EXP = "[^\\W_]{3,16}";
    private static final String LOGIN_FAILED_CODE = "E001";

    private Hashing hashing = new Hashing();
    private String pass;
    private String json;
    private String key;

    @Override
    public void validate(String password) {
        if (!password.matches(CONVENTION_EXP)) {
            throw new AuthorizationFailedException(Exceptions.WRONG_PASSWORD_FORMAT);
        }
        pass = password;
    }

    void validateJson(String json) {
        if (json.startsWith(LOGIN_FAILED_CODE)) {
            throw new AuthorizationFailedException(Exceptions.WRONG_PASSWORD);
        }
        this.json = json;
        key = hashing.generate(pass);
        pass = null;
    }

    public void validateCheckedPassword(String password) {
        String hash = hashing.generate(password);
        if (!hash.equals(key)) {
            throw new AuthorizationFailedException(Exceptions.UNLOCK_FAILED);
        }
    }

    @Override
    public String extract() {
        String result = json;
        json = null;
        return result;
    }
}
