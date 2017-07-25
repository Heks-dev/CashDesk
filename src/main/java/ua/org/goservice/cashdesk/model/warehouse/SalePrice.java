package ua.org.goservice.cashdesk.model.warehouse;

import java.math.BigDecimal;

public class SalePrice {

    private final Integer id;
    private final Integer goodid;
    private final String name;
    private final String barcode;
    private final String measures;
    private final BigDecimal price;

    SalePrice(Integer id, Integer goodid, String name, String barcode,
                     String measures, BigDecimal price) {
        this.id = id;
        this.goodid = goodid;
        this.name = name;
        this.barcode = barcode;
        this.measures = measures;
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public Integer getGoodid() {
        return goodid;
    }

    public String getName() {
        return name;
    }

    public String getBarcode() {
        return barcode;
    }

    public String getMeasures() {
        return measures;
    }

    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return barcode + " " + name;
    }
}
