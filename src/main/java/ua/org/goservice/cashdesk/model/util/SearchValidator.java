package ua.org.goservice.cashdesk.model.util;

import ua.org.goservice.cashdesk.model.exception.Exceptions;
import ua.org.goservice.cashdesk.model.exception.NotFoundException;

public class SearchValidator implements Validator<String> {
    private static final String NOT_FOUND_CODE = "E502";

    @Override
    public void validate(String val) {
        if (val.startsWith(NOT_FOUND_CODE)) {
            throw new NotFoundException(Exceptions.DISCOUNT_NOT_FOUND);
        }
    }
}
