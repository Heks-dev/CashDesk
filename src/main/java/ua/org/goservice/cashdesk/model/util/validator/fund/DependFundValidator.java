package ua.org.goservice.cashdesk.model.util.validator.fund;

import ua.org.goservice.cashdesk.model.exception.Exceptions;

import java.math.BigDecimal;

public class DependFundValidator extends FundValidator {

    private final BigDecimal totalContributed;
    private final BigDecimal currentFundAmout;
    private final BigDecimal toPay;

    public DependFundValidator(BigDecimal totalContributed, BigDecimal currentFundAmount, BigDecimal toPay) {
        this.totalContributed = totalContributed;
        this.currentFundAmout = currentFundAmount;
        this.toPay = toPay;
    }

    @Override
    public void validate(String val) {
        super.validate(val);
        BigDecimal comparable;
        if (totalContributed != null) {
            comparable = totalContributed;
            if (currentFundAmout != null) {
                comparable = totalContributed.subtract(currentFundAmout);
            }
            comparable = comparable.add(getValidFund());
        } else {
            comparable = getValidFund();
        }
        compare(comparable);
    }

    private void compare(BigDecimal comparable) {
        if (comparable.compareTo(toPay) > 0) {
            throw new IllegalArgumentException(Exceptions.UNABLE_ADD_MORE_FUNDS);
        }
    }
}
