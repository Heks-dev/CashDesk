package ua.org.goservice.cashdesk.model.trade.transaction;

import java.math.BigDecimal;

class SaleUnit {
    private final Integer idgood;
    private final BigDecimal countgood;
    private final BigDecimal price;

    SaleUnit(Integer idgood, BigDecimal countgood, BigDecimal price) {
        this.idgood = idgood;
        this.countgood = countgood;
        this.price = price;
    }

    public Integer getIdgood() {
        return idgood;
    }

    public BigDecimal getCountgood() {
        return countgood;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
