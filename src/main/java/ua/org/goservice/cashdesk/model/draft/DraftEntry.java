package ua.org.goservice.cashdesk.model.draft;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import ua.org.goservice.cashdesk.model.warehouse.Product;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class DraftEntry {

    private final StringProperty barcode;
    private final StringProperty name;
    private final StringProperty measure;
    private final ObjectProperty<BigDecimal> price;
    private final ObjectProperty<BigDecimal> count;
    private final ObjectProperty<BigDecimal> totalSum;

    public DraftEntry(Product product, BigDecimal count) {
        this.barcode = new SimpleStringProperty(product.getBarcode());
        this.name = new SimpleStringProperty(product.getName());
        this.measure = new SimpleStringProperty(product.getMeasures());
        this.price = new SimpleObjectProperty<>(product.getPrice());
        this.count = new SimpleObjectProperty<>(count);
        this.totalSum = new SimpleObjectProperty<>(new BigDecimal(0));
        calculateTotalSum();
    }

    public String getBarcode() {
        return barcode.get();
    }

    public StringProperty barcodeProperty() {
        return barcode;
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public String getMeasure() {
        return measure.get();
    }

    public StringProperty measureProperty() {
        return measure;
    }

    public BigDecimal getCount() {
        return count.get();
    }

    public ObjectProperty<BigDecimal> countProperty() {
        return count;
    }

    public BigDecimal getPrice() {
        return price.get();
    }

    public ObjectProperty<BigDecimal> priceProperty() {
        return price;
    }

    public BigDecimal getTotalSum() {
        return totalSum.get();
    }

    public ObjectProperty<BigDecimal> totalSumProperty() {
        return totalSum;
    }

    public void setQuantity(BigDecimal count) {
        this.count.setValue(count);
        calculateTotalSum();
    }

    public void addQuantity(BigDecimal count) {
        this.count.setValue(this.count.getValue().add(count)
                .setScale(2, RoundingMode.HALF_UP));
        calculateTotalSum();
    }


    private void calculateTotalSum() {
        totalSum.setValue(price.getValue().multiply(count.getValue())
                .setScale(2, RoundingMode.HALF_UP));
    }

    @Override
    public String toString() {
        return barcode.getValue() + " " + name.getValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DraftEntry that = (DraftEntry) o;

        if (barcode != null ? !barcode.equals(that.barcode) : that.barcode != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (measure != null ? !measure.equals(that.measure) : that.measure != null) return false;
        if (price != null ? !price.equals(that.price) : that.price != null) return false;
        if (count != null ? !count.equals(that.count) : that.count != null) return false;
        return totalSum != null ? totalSum.equals(that.totalSum) : that.totalSum == null;
    }

    @Override
    public int hashCode() {
        int result = barcode != null ? barcode.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (measure != null ? measure.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (count != null ? count.hashCode() : 0);
        result = 31 * result + (totalSum != null ? totalSum.hashCode() : 0);
        return result;
    }
}
