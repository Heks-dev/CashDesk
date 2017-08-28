package ua.org.goservice.cashdesk.model.trade.transaction;

import java.math.BigDecimal;
import java.util.List;

public class RefundContent extends TransactionContent {

    private final Integer reasonid;

    RefundContent(Integer id, Integer idfrom, Integer idfor, BigDecimal cash_payment, BigDecimal bonus_payment, List<SaleUnit> arraygoods, Integer reasonid) {
        super(id, idfrom, idfor, cash_payment, bonus_payment, arraygoods);
        this.reasonid = reasonid;
    }

    public Integer getReasonid() {
        return reasonid;
    }
}
