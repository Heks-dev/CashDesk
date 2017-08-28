package ua.org.goservice.cashdesk.model.draft;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import ua.org.goservice.cashdesk.model.warehouse.Product;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class RefundDraftEntry extends DraftEntry {

    private ObjectProperty<BigDecimal> refundCount;
    private ObjectProperty<BigDecimal> totalRefundSum;

    public RefundDraftEntry(Product product, BigDecimal count) {
        super(product, count);
        refundCount = new SimpleObjectProperty<>(null);
        totalRefundSum = new SimpleObjectProperty<>(null);
    }

    public void setRefundCount(BigDecimal refundCount) {
        this.refundCount.set(refundCount);
        calculateRefundSum();
    }

    public BigDecimal getRefundCount() {
        return refundCount.get();
    }

    public ObjectProperty<BigDecimal> refundCountProperty() {
        return refundCount;
    }

    public BigDecimal getTotalRefundSum() {
        return totalRefundSum.get();
    }

    public ObjectProperty<BigDecimal> totalRefundSumProperty() {
        return totalRefundSum;
    }

    private void calculateRefundSum() {
        if (refundCount.getValue() != null) {
            totalRefundSum.setValue(refundCount.getValue()
                    .multiply(getPrice()).setScale(2, RoundingMode.HALF_UP));
        } else {
            totalRefundSum.setValue(null);
        }
    }
}
