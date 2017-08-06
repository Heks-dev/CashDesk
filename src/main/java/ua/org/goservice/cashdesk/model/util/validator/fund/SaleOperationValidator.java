package ua.org.goservice.cashdesk.model.util.validator.fund;

import ua.org.goservice.cashdesk.model.exception.ActionDeniedException;
import ua.org.goservice.cashdesk.model.exception.Exceptions;
import ua.org.goservice.cashdesk.model.util.validator.Validator;

public class SaleOperationValidator implements Validator<String> {

    private static final String CHECK_AMOUNT_ERROR = "E112";

    @Override
    public void validate(String val) {
        if (val.startsWith(CHECK_AMOUNT_ERROR)) {
            throw new ActionDeniedException(Exceptions.AMOUNT_NOT_MATCH);
        }
    }
}
