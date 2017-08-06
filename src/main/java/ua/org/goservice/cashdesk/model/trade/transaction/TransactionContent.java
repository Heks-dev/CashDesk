package ua.org.goservice.cashdesk.model.trade.transaction;

import java.math.BigDecimal;
import java.util.List;

public abstract class TransactionContent {

    private String active;
    private final Integer idfrom;
    private final Integer idfor;
    private final Integer priceid;
    private final BigDecimal cash_payment;
    private final BigDecimal bonus_payment;
    private final Long bonus_card;
    private final List<SaleUnit> arraygoods;

    TransactionContent(Integer idfrom, Integer idfor, Integer priceid,
                              BigDecimal cash_payment, BigDecimal bonus_payment,
                              Long bonus_card, List<SaleUnit> arraygoods) {
        this.idfrom = idfrom;
        this.idfor = idfor;
        this.priceid = priceid;
        this.cash_payment = cash_payment;
        this.bonus_payment = bonus_payment;
        this.bonus_card = bonus_card;
        this.arraygoods = arraygoods;
    }

    void setActive(String active) {
        this.active = active;
    }

    public String getActive() {
        return active;
    }

    public Integer getIdfrom() {
        return idfrom;
    }

    public Integer getIdfor() {
        return idfor;
    }

    public Integer getPriceid() {
        return priceid;
    }

    public BigDecimal getCash_payment() {
        return cash_payment;
    }

    public BigDecimal getBonus_payment() {
        return bonus_payment;
    }

    public Long getBonus_card() {
        return bonus_card;
    }

    public List<SaleUnit> getArraygoods() {
        return arraygoods;
    }
}
