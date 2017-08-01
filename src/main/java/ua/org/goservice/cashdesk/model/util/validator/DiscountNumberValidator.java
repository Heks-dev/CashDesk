package ua.org.goservice.cashdesk.model.util.validator;

import ua.org.goservice.cashdesk.model.exception.Exceptions;

public class DiscountNumberValidator implements Validator<String> {
    private static final String DISCOUNT_NUM_EXP = "(\\d){13}";

    private Long validatedNumber;

    @Override
    public void validate(String val) {
        if (!val.matches(DISCOUNT_NUM_EXP)) {
            throw new IllegalArgumentException(Exceptions.INVALID_DISCOUNT_CARD_NUMBER_FORMAT);
        }
        validatedNumber = Long.valueOf(val);
    }

    public Long getValidatedNumber() {
        return validatedNumber;
    }
}
