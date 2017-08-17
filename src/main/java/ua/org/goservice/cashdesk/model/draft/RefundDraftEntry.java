package ua.org.goservice.cashdesk.model.draft;

import ua.org.goservice.cashdesk.model.warehouse.Product;

import java.math.BigDecimal;

public class RefundDraftEntry extends DraftEntry {

    private BigDecimal refundCount;

    public RefundDraftEntry(Product product, BigDecimal count) {
        super(product, count);
    }

    public void setRefundCount(BigDecimal refundCount) {
        this.refundCount = refundCount;
    }

    public BigDecimal getRefundCount() {
        return refundCount;
    }
}
