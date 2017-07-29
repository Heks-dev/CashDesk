package ua.org.goservice.cashdesk.controller.cashdesk.sale.validator;

import ua.org.goservice.cashdesk.model.exception.Exceptions;
import ua.org.goservice.cashdesk.model.util.Validator;

import java.math.BigDecimal;

public class FundValidator implements Validator<String> {
    private static final String CURRENCY_FORMAT = "(\\d+)|(\\d+)(.|,)(\\d){1,2}";
    private static final String COMMA_SEPARATOR = ".+(,).+";
    private BigDecimal validFundValue;
    @Override
    public void validate(String val) {
        if (!val.matches(CURRENCY_FORMAT)) {
            throw new IllegalArgumentException(Exceptions.INVALID_CURRENCY_FORMAT);
        }
        val = replaceComma(val);
        validFundValue = new BigDecimal(val);
    }

    private String replaceComma(String val) {
        if (val.matches(COMMA_SEPARATOR)) {
            val = val.replace(",", ".");
        }
        return val;
    }

    public BigDecimal getValidFund() {
        return validFundValue;
    }
}
