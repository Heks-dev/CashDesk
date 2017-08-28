package ua.org.goservice.cashdesk.model.util.validator;

import ua.org.goservice.cashdesk.model.draft.RefundDraft;
import ua.org.goservice.cashdesk.model.exception.ActionDeniedException;
import ua.org.goservice.cashdesk.model.exception.Exceptions;

import java.math.BigDecimal;

public class ComplexRefundValidator implements Validator<RefundDraft> {

    @Override
    public void validate(RefundDraft val) {
        if (val != null) {
            validateAmountToRefund(val);
            validateFunds(val);
        }
    }

    private void validateAmountToRefund(RefundDraft val) {
        if (val.getAmountToRefund() == null) {
            throw new ActionDeniedException(Exceptions.EMPTY_REFUND_LIST);
        }
    }

    private void validateFunds(RefundDraft val) {
        BigDecimal totalContributedFunds = val.getTotalContributedFunds();
        if (totalContributedFunds == null || totalContributedFunds.compareTo(val.getAmountToRefund()) != 0) {
            throw new ActionDeniedException(Exceptions.REFUND_AMOUNT_EXCEEDED);
        }
    }
}
