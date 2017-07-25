package ua.org.goservice.cashdesk.model.draft;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ua.org.goservice.cashdesk.controller.cashdesk.sale.SaleUIAssistant;
import ua.org.goservice.cashdesk.model.warehouse.Product;
import ua.org.goservice.cashdesk.model.warehouse.SalePrice;

import java.math.BigDecimal;

public class Draft {

    private final SaleUIAssistant uiAssistant;
    private ObservableList<DraftEntry> draftList;

    public Draft(SaleUIAssistant uiAssistant) {
        this.uiAssistant = uiAssistant;
        this.draftList = FXCollections.observableArrayList();
    }

    public void addProduct(Product product, SalePrice salePrice, BigDecimal count) {
        System.out.println("here is add method!");
    }

    public ObservableList<DraftEntry> getDraftList() {
        return FXCollections.unmodifiableObservableList(draftList);
    }
}
