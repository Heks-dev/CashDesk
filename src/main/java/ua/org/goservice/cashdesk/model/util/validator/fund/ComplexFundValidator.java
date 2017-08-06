package ua.org.goservice.cashdesk.model.util.validator.fund;

import ua.org.goservice.cashdesk.model.draft.Draft;
import ua.org.goservice.cashdesk.model.draft.PaymentMethod;
import ua.org.goservice.cashdesk.model.exception.ActionDeniedException;
import ua.org.goservice.cashdesk.model.exception.Exceptions;
import ua.org.goservice.cashdesk.model.util.validator.Validator;

import java.math.BigDecimal;

public class ComplexFundValidator implements Validator<Draft> {

    private BigDecimal amountInCash;

    @Override
    public void validate(Draft val) {
        int compared = val.getTotalContributedFunds().compareTo(val.getAmountToPay());
        if (noChange(compared, val)) return;
        checkForScarcity(compared);
        boolean hasChange = compared > 0;
        if (hasChange) {
            calculateChange(val);
        }
    }

    private boolean noChange(int compared, Draft val) {
        boolean noChange = compared == 0;
        if (noChange) {
            amountInCash = val.getCashFund();
            return true;
        }
        return false;
    }

    private void checkForScarcity(int compared) {
        boolean scarcity = compared < 0;
        if (scarcity) {
            throw new ActionDeniedException(Exceptions.INSUFFICIENT_FUNDS);
        }
    }

    private void calculateChange(Draft val) {
        BigDecimal terminal = val.getTerminalFund();
        BigDecimal bonuses = val.getBonusFund();
        checkForExceeded(val.getAmountToPay(), terminal, bonuses);
        amountInCash = val.getCashFund().subtract(val.getOdds());
    }

    private void checkForExceeded(BigDecimal amountToPay, BigDecimal terminal, BigDecimal bonuses) {
        if (terminal != null && bonuses != null) {
            if (terminal.add(bonuses).compareTo(amountToPay) > 0) {
                throw new ActionDeniedException(Exceptions.BOTH_PAYMENT_AMOUNT_EXCEEDED);
            }
        }
        if (terminal != null) checkForExceededPaymentAmount(PaymentMethod.TERMINAL,
                terminal, amountToPay);
        if (bonuses != null) checkForExceededPaymentAmount(PaymentMethod.BONUSES,
                bonuses, amountToPay);
    }

    private void checkForExceededPaymentAmount(PaymentMethod method, BigDecimal contributed, BigDecimal amountToPay) {
        if (contributed.compareTo(amountToPay) > 0) {
            throw new ActionDeniedException(String.format(Exceptions.PAYMENT_AMOUNT_EXCEEDED,
                    method.getValue()));
        }
    }

    public BigDecimal getAmountInCash() {
        return amountInCash;
    }
}
