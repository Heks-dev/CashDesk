package ua.org.goservice.cashdesk.controller.cashdesk.sale.validator;

import ua.org.goservice.cashdesk.model.exception.Exceptions;
import ua.org.goservice.cashdesk.model.util.Validator;

public class DiscountNumberValidator implements Validator<String> {
    private static final String DISCOUNT_NUM_EXP = "(\\d){13}";
    @Override
    public void validate(String val) {
        if (!val.matches(DISCOUNT_NUM_EXP)) {
            throw new IllegalArgumentException(Exceptions.INVALID_DISCOUNT_CARD_NUMBER_FORMAT);
        }
    }
}
