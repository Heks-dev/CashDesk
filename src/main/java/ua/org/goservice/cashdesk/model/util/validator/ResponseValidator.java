package ua.org.goservice.cashdesk.model.util.validator;

import ua.org.goservice.cashdesk.model.exception.ActionDeniedException;

public class ResponseValidator implements Validator<String> {

    private static final int ERROR_CODE_SIZE = 4;
    private static final String ERROR_MESSAGE = "Произошла ошибка %s";

    @Override
    public void validate(String val) {
        if (!val.startsWith("OK")) {
            String errorCode = val.substring(0, ERROR_CODE_SIZE);
            throw new ActionDeniedException(String.format(ERROR_MESSAGE, errorCode));
        }
    }
}
