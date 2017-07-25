package ua.org.goservice.cashdesk.model.warehouse;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ua.org.goservice.cashdesk.model.api.ApiFilter;
import ua.org.goservice.cashdesk.model.api.ApiUrl;
import ua.org.goservice.cashdesk.model.api.ApiVal;
import ua.org.goservice.cashdesk.model.communication.HttpRequestExecutor;
import ua.org.goservice.cashdesk.model.communication.RequestExecutor;
import ua.org.goservice.cashdesk.model.communication.request.Filter;
import ua.org.goservice.cashdesk.model.communication.request.FilterSet;
import ua.org.goservice.cashdesk.model.communication.request.RequestBuilder;
import ua.org.goservice.cashdesk.model.organization.Organization;
import ua.org.goservice.cashdesk.model.util.json.JsonAgent;
import ua.org.goservice.cashdesk.model.util.json.JsonFormat;
import ua.org.goservice.cashdesk.model.util.json.Token;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Warehouse {

    private final RequestExecutor requestExecutor = new HttpRequestExecutor();
    private ObservableList<Product> products = FXCollections.observableArrayList();
    private Map<String, Product> namedProductStorage;
    private Map<String, SalePrice> barcodePriceStorage;

    public Warehouse(Integer storeid, Integer priceid, Organization currentBuyer) {
        syncProducts(storeid, priceid);
        mappingProductsWithProductName(products);
        loadCurrentPriceList(currentBuyer, storeid);
    }

    public void syncStoreProducts(Integer storeid, Integer priceid) {
        syncProducts(storeid, priceid);
        mappingProductsWithProductName(products);
    }

    private void syncProducts(Integer storeid, Integer priceid) {
        requestExecutor.sendRequest(new RequestBuilder(ApiUrl.STOREHOUSE, ApiVal.LIST,
                new FilterSet(
                        new Filter(ApiFilter.STORE_ID, storeid),
                        new Filter(ApiFilter.PRICE_ID, priceid)
                )));
        String json = requestExecutor.getResponse();
        // debug
        System.out.println(json);
        List<Product> productList = JsonAgent.deserialize(json, Token.PRODUCT_LIST);
        products.setAll(productList);
    }

    private void mappingProductsWithProductName(ObservableList<Product> products) {
        namedProductStorage = new HashMap<>();
        for (Product product : products) {
            namedProductStorage.put(product.toString(), product);
        }
    }

    public void loadCurrentBuyerPriceList(Organization currentBuyer, Integer storeid) {
        loadCurrentPriceList(currentBuyer, storeid);
    }

    private void loadCurrentPriceList(Organization currentBuyer, Integer storeid) {
        // todo validation current buyer
        requestExecutor.sendRequest(new RequestBuilder(ApiUrl.STOREHOUSE, ApiVal.LIST,
                new FilterSet(
                        new Filter(ApiFilter.PRICE_ID, currentBuyer.getPriceid()),
                        new Filter(ApiFilter.STORE_ID, storeid)
                )));
        String json = requestExecutor.getResponse();
        // debug
        System.out.println(json);
        List<SalePrice> priceList = JsonAgent.deserialize(json, Token.SALE_PRICE_LIST);
        mappingSalePriceWithBarcode(priceList);
    }

    private void mappingSalePriceWithBarcode(List<SalePrice> priceList) {
        barcodePriceStorage = new HashMap<>();
        for (SalePrice salePrice : priceList) {
            barcodePriceStorage.put(salePrice.getBarcode(), salePrice);
        }
    }

    public Product getProductByName(String name) {
        return namedProductStorage.get(name);
    }

    public SalePrice getPriceByBarcode(String barcode) {
        return barcodePriceStorage.get(barcode);
    }

    public BigDecimal getActualProductCount(Product product) {
        requestExecutor.sendRequest(new RequestBuilder(ApiUrl.STOREHOUSE, ApiVal.LIST,
                new FilterSet(
                        new Filter(ApiFilter.PRICE_ID, product.getPriceid()),
                        new Filter(ApiFilter.STORE_ID, product.getStoreid()),
                        new Filter(ApiFilter.BARCODE, product.getBarcode())
                )));
        String json = requestExecutor.getResponse();
        System.out.println(json);
        Product newedProductInfo = JsonAgent.deserialize(json, Product.class, JsonFormat.SINGLE_OBJECT);
        return newedProductInfo.getCount();
    }

    public ObservableList<Product> getProducts() {
        return FXCollections.unmodifiableObservableList(products);
    }
}
