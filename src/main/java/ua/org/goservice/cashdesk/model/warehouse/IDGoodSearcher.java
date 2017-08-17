package ua.org.goservice.cashdesk.model.warehouse;

public interface IDGoodSearcher {

    Integer findGoodID(String barcode);

    Product findProduct(Integer goodID);
}
