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

public class Warehouse implements IDGoodSearcher {

    private final RequestExecutor requestExecutor = new HttpRequestExecutor();
    private final Integer storeid;
    private ObservableList<Product> priceList = FXCollections.observableArrayList();
    private Map<String, Product> namedProductStorage;
    private Map<String, Product> barcodeProductStorage;

    public Warehouse(Integer storeid, Organization currentBuyer) {
        this.storeid = storeid;
        syncPriceList(currentBuyer);
    }

    public void syncPriceList(Organization currentBuyer) {
        sync(currentBuyer);
        mappingProducts();
    }

    private void sync(Organization currentBuyer) {
        requestExecutor.sendRequest(new RequestBuilder(ApiUrl.STOREHOUSE, ApiVal.LIST,
                new FilterSet(
                        new Filter(ApiFilter.STORE_ID, storeid),
                        new Filter(ApiFilter.PRICE_ID, currentBuyer.getPriceid())
                )));
        String json = requestExecutor.getResponse();
        // todo debug
        System.out.println(json);
        List<Product> productList = JsonAgent.deserialize(json, Token.PRODUCT_LIST);
        priceList.setAll(productList);
    }

    private void mappingProducts() {
        namedProductStorage = new HashMap<>();
        barcodeProductStorage = new HashMap<>();
        for (Product product : priceList) {
            namedProductStorage.put(product.toString(), product);
            barcodeProductStorage.put(product.getBarcode(), product);
        }
    }

    @Override
    public Integer findGoodID(String barcode) {
        return barcodeProductStorage.get(barcode).getGoodid();
    }

    public Product getProductByName(String name) {
        return namedProductStorage.get(name);
    }

    public BigDecimal getActualProductCount(String barcode) {
        Product product = barcodeProductStorage.get(barcode);
        requestExecutor.sendRequest(new RequestBuilder(ApiUrl.STOREHOUSE, ApiVal.LIST,
                new FilterSet(
                        new Filter(ApiFilter.PRICE_ID, product.getPriceid()),
                        new Filter(ApiFilter.STORE_ID, product.getStoreid()),
                        new Filter(ApiFilter.BARCODE, product.getBarcode())
                )));
        String json = requestExecutor.getResponse();
        // todo debug
        System.out.println(json);
        Product newedProductInfo = JsonAgent.deserialize(json, Product.class, JsonFormat.SINGLE_OBJECT);
        return newedProductInfo.getCount();
    }

    public ObservableList<Product> getPriceList() {
        return FXCollections.unmodifiableObservableList(priceList);
    }
}
