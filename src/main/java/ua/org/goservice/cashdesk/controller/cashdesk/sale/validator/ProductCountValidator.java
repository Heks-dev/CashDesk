package ua.org.goservice.cashdesk.controller.cashdesk.sale.validator;

import ua.org.goservice.cashdesk.model.util.Validator;

import java.math.BigDecimal;

public class ProductCountValidator implements Validator<String> {

    private static final String APIECE_MEASURE = "шт";
    private static final String PIECE_EXP = "(\\d+)|(\\d+)(.|,)(0){1,2}";
    private static final String WEIGHING_EXP = "(\\d+)|(\\d+)(.|,)(\\d){1,2}";
    private final String measure;
    private final BigDecimal available;
    private boolean isApiece;

    ProductCountValidator() {
        this(null, null);
    }

    ProductCountValidator(String measure) {
        this(measure, null);
    }

    public ProductCountValidator(String measure, BigDecimal available) {
        this.measure = measure;
        this.available = available;
    }

    @Override
    public void validate(String val) {
        isApieceFormat(val);
        if (measure != null) {
            validateMeasure();
        }
        if (available != null) {
            checkAvailability(val);
        }
    }

    private void isApieceFormat(String val) {
        if (val.matches(PIECE_EXP)) {
            isApiece = true;
        } else if (val.matches(WEIGHING_EXP)) {
            isApiece = false;
        } else {
            throw new IllegalArgumentException();
        }
    }

    private void validateMeasure() {
        if (measure.equals(APIECE_MEASURE) && !isApiece) {
            throw new IllegalArgumentException();
        }
    }

    private void checkAvailability(String val) {
        BigDecimal desiredCount = new BigDecimal(val);
        if (desiredCount.compareTo(available) > 0) {
            throw new IllegalArgumentException();
        }
    }
}
