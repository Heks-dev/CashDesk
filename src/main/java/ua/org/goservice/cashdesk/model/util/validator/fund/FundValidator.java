package ua.org.goservice.cashdesk.model.util.validator.fund;

import ua.org.goservice.cashdesk.model.exception.Exceptions;
import ua.org.goservice.cashdesk.model.util.validator.Validator;

import java.math.BigDecimal;

public class FundValidator implements Validator<String> {
    private static final String CURRENCY_FORMAT = "(\\d+)|(\\d+)(.|,)(\\d){1,2}";
    private static final String COMMA_SEPARATOR = ".+(,).+";
    private BigDecimal validatedFundValue;
    @Override
    public void validate(String val) {
        if (!val.matches(CURRENCY_FORMAT)) {
            throw new IllegalArgumentException(Exceptions.INVALID_CURRENCY_FORMAT);
        }
        setValidatedFundValue(val);
    }

    private void setValidatedFundValue(String val) {
        if (val.matches(COMMA_SEPARATOR)) {
            val = val.replace(",", ".");
        }
        validatedFundValue = new BigDecimal(val);
    }

    public BigDecimal getValidFund() {
        return validatedFundValue;
    }
}
