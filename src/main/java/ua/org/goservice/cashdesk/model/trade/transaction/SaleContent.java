package ua.org.goservice.cashdesk.model.trade.transaction;

import java.math.BigDecimal;
import java.util.List;

public class SaleContent extends TransactionContent {

    private final BigDecimal terminal_payment;

    SaleContent(Integer idfrom, Integer idfor, Integer priceid,
                BigDecimal cash_payment, BigDecimal terminal_payment, BigDecimal bonus_payment,
                Long bonus_card, List<SaleUnit> arraygoods)
    {
        super(idfrom, idfor, priceid, cash_payment, bonus_payment, bonus_card, arraygoods);
        this.terminal_payment = terminal_payment;
    }

    public BigDecimal getTerminal_payment() {
        return terminal_payment;
    }
}
