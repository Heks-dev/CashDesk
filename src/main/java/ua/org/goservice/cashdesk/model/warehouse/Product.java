package ua.org.goservice.cashdesk.model.warehouse;

import java.math.BigDecimal;

public class Product {
    private final String name;
    private final String barcode;
    private final String measures;
    private final BigDecimal price;
    private final BigDecimal count;
    private final Integer id;
    private final Integer goodid;
    private final Integer kindid;
    private final Integer brandid;
    private final Integer priceid;
    private final Integer storeid;

    public Product(String name, String barcode, String measures, BigDecimal price, BigDecimal count, Integer id, Integer goodid, Integer kindid, Integer brandid, Integer priceid, Integer storeid) {
        this.name = name;
        this.barcode = barcode;
        this.measures = measures;
        this.price = price;
        this.count = count;
        this.id = id;
        this.goodid = goodid;
        this.kindid = kindid;
        this.brandid = brandid;
        this.priceid = priceid;
        this.storeid = storeid;
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

    public BigDecimal getCount() {
        return count;
    }

    public Integer getId() {
        return id;
    }

    public Integer getGoodid() {
        return goodid;
    }

    public Integer getKindid() {
        return kindid;
    }

    public Integer getBrandid() {
        return brandid;
    }

    public Integer getPriceid() {
        return priceid;
    }

    public Integer getStoreid() {
        return storeid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        if (name != null ? !name.equals(product.name) : product.name != null) return false;
        if (barcode != null ? !barcode.equals(product.barcode) : product.barcode != null) return false;
        if (measures != null ? !measures.equals(product.measures) : product.measures != null) return false;
        if (price != null ? !price.equals(product.price) : product.price != null) return false;
        if (count != null ? !count.equals(product.count) : product.count != null) return false;
        if (id != null ? !id.equals(product.id) : product.id != null) return false;
        if (goodid != null ? !goodid.equals(product.goodid) : product.goodid != null) return false;
        if (kindid != null ? !kindid.equals(product.kindid) : product.kindid != null) return false;
        if (brandid != null ? !brandid.equals(product.brandid) : product.brandid != null) return false;
        if (priceid != null ? !priceid.equals(product.priceid) : product.priceid != null) return false;
        return storeid != null ? storeid.equals(product.storeid) : product.storeid == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (barcode != null ? barcode.hashCode() : 0);
        result = 31 * result + (measures != null ? measures.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (count != null ? count.hashCode() : 0);
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (goodid != null ? goodid.hashCode() : 0);
        result = 31 * result + (kindid != null ? kindid.hashCode() : 0);
        result = 31 * result + (brandid != null ? brandid.hashCode() : 0);
        result = 31 * result + (priceid != null ? priceid.hashCode() : 0);
        result = 31 * result + (storeid != null ? storeid.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return barcode + " " + name;
    }
}
