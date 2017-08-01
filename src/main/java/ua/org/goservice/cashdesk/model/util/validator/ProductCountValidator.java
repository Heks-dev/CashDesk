package ua.org.goservice.cashdesk.model.util.validator;

import ua.org.goservice.cashdesk.model.exception.Exceptions;

import java.math.BigDecimal;

public class ProductCountValidator implements Validator<String> {

    private static final String APIECE_MEASURE = "шт";
    private static final String PIECE_EXP = "(\\d+)|(\\d+)(.|,)(0){1,2}";
    private static final String WEIGHING_EXP = "(\\d+)|(\\d+)(.|,)(\\d){1,2}";
    private static final String COMMA_SEPARATOR = ".+(,).+";

    private final String measure;
    private final BigDecimal available;
    private boolean isApiece;
    private BigDecimal desiredQuantity;

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
        setDesiredCount(val);
    }

    private void isApieceFormat(String val) {
        if (val.matches(PIECE_EXP)) {
            isApiece = true;
        } else if (val.matches(WEIGHING_EXP)) {
            isApiece = false;
        } else {
            throw new IllegalArgumentException(Exceptions.INCORRECT_MEASURE_FORMAT);
        }
    }

    private void validateMeasure() {
        if (measure.equals(APIECE_MEASURE) && !isApiece) {
            throw new IllegalArgumentException(Exceptions.INCORRECT_MEASURE_APIECE_FORMAT);
        }
    }

    private void checkAvailability(String val) {
        BigDecimal desiredCount = new BigDecimal(val);
        if (desiredCount.compareTo(available) > 0) {
            throw new IllegalArgumentException(Exceptions.EXCEED_AVAILABLE_QUANTITY);
        }
    }

    private void setDesiredCount(String val) {
        if (val.matches(COMMA_SEPARATOR)) {
            val = val.replace(",", ".");
        }
        desiredQuantity = new BigDecimal(val);
    }

    public BigDecimal getDesiredQuantity() {
        return desiredQuantity;
    }

    public BigDecimal getAvailable() {
        return available;
    }
}
