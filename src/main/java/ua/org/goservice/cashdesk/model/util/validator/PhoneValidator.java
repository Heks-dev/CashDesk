package ua.org.goservice.cashdesk.model.util.validator;

import ua.org.goservice.cashdesk.model.exception.Exceptions;

public class PhoneValidator implements Validator<String> {
    private static final String CONVENTION = "^\\+3(\\d){11}$";
    @Override
    public void validate(String phone) {
        if (!phone.matches(CONVENTION)) {
            throw new IllegalArgumentException(Exceptions.INCORRECT_PHONE_NUM);
        }
    }
}
