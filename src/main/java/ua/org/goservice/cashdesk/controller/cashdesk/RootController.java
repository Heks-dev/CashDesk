package ua.org.goservice.cashdesk.controller.cashdesk;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import ua.org.goservice.cashdesk.controller.auth.ScreenLocker;
import ua.org.goservice.cashdesk.model.organization.OrganizationManager;
import ua.org.goservice.cashdesk.model.warehouse.Warehouse;

public class RootController {
    static final String LOCATION = "/view/cashdesk/root.fxml";
    static final String TITLE = "GoService - CASH DESK 1.0";
    @FXML
    private TabPane tabPane;
    @FXML
    private Tab saleTab;

    private CashDeskManager cashDeskManager;

    private OrganizationManager organizationManager;
    private Warehouse warehouse;
    private ScreenLocker screenLocker;

    public void handleSynchronize() {
        organizationManager.syncBuyers();
        warehouse.syncPriceList(organizationManager.getCurrentBuyer());
    }

    public void handleLockScreen() {
        screenLocker.lockScreen();
    }

    public void handleLogOut() {
        cashDeskManager.logOut();
    }

    void setDependencies(AnchorPane salePane, CashDeskManager cashDeskManager,
                         OrganizationManager organizationManager,
                         Warehouse warehouse, ScreenLocker screenLocker)
    {
        saleTab.setContent(salePane);
        this.cashDeskManager = cashDeskManager;
        this.organizationManager = organizationManager;
        this.warehouse = warehouse;
        this.screenLocker = screenLocker;
    }
}
