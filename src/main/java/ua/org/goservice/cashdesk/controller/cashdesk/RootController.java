package ua.org.goservice.cashdesk.controller.cashdesk;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import ua.org.goservice.cashdesk.controller.auth.ScreenLocker;
import ua.org.goservice.cashdesk.controller.cashdesk.sale.SaleController;
import ua.org.goservice.cashdesk.controller.dialogs.Alerts;
import ua.org.goservice.cashdesk.controller.dialogs.alert.AlertCause;
import ua.org.goservice.cashdesk.model.exception.CommunicationException;
import ua.org.goservice.cashdesk.model.organization.OrganizationManager;
import ua.org.goservice.cashdesk.model.warehouse.Warehouse;

public class RootController {
    static final String LOCATION = "/view/cashdesk/root.fxml";
    static final String TITLE = "GoService - CASH DESK 1.0";
    @FXML
    private TabPane tabPane;
    @FXML
    private Tab saleTab;
    @FXML
    private Tab cardTab;
    @FXML
    private Tab refundTab;
    @FXML
    private Tab reportTab;

    private CashDeskManager cashDeskManager;

    private OrganizationManager organizationManager;
    private Warehouse warehouse;
    private ScreenLocker screenLocker;
    private Stage stage;

    @FXML
    private void handleSynchronize() {
        try {
            organizationManager.syncBuyers();
            warehouse.syncPriceList(organizationManager.getCurrentBuyer());
        } catch (CommunicationException e) {
            AlertCause cause = AlertCause.SYNCHRONIZATION_PROBLEM;
            cause.setContent(e.getMessage());
            Alerts.notifying(stage, cause);
        }
    }

    @FXML
    private void handleLockScreen() {
        screenLocker.lockScreen();
    }

    @FXML
    private void handleLogOut() {
        if (Alerts.confirm(stage, AlertCause.LOG_OUT)) {
            cashDeskManager.logOut();
        }
    }

    void setDependencies(Stage stage, FXMLLoader saleLoader, CashDeskManager cashDeskManager,
                         OrganizationManager organizationManager,
                         Warehouse warehouse, ScreenLocker screenLocker, Scene scene)
    {
        this.stage = stage;
        setTabContent(saleLoader.getRoot());
        this.cashDeskManager = cashDeskManager;
        this.organizationManager = organizationManager;
        this.warehouse = warehouse;
        this.screenLocker = screenLocker;
        bindTabHotKeys(scene);
        bindSalePaneHotKeys(saleLoader.getController(), scene);
    }

    private void bindSalePaneHotKeys(SaleController controller, Scene scene) {
        scene.setOnKeyPressed(event -> {
            if (tabPane.getSelectionModel().getSelectedItem().equals(saleTab)) {
                if (event.getCode() == KeyCode.F5) {
                    controller.callContributeCashFund();
                }
                if (event.getCode() == KeyCode.F6) {
                    controller.callContributeTerminalFund();
                }
                if (event.getCode() == KeyCode.F7) {
                    controller.callContributeBonusesFund();
                }
            }
        });
    }

    private void setTabContent(AnchorPane salePane) {
        saleTab.setContent(salePane);
    }

    private void bindTabHotKeys(Scene scene) {
        scene.getAccelerators().put(new KeyCodeCombination(KeyCode.DIGIT1,
                KeyCombination.ALT_ANY), () -> tabPane.getSelectionModel().select(saleTab));
        scene.getAccelerators().put(new KeyCodeCombination(KeyCode.DIGIT2,
                KeyCombination.ALT_ANY), () -> tabPane.getSelectionModel().select(cardTab));
        scene.getAccelerators().put(new KeyCodeCombination(KeyCode.DIGIT3,
                KeyCombination.ALT_ANY), () -> tabPane.getSelectionModel().select(refundTab));
        scene.getAccelerators().put(new KeyCodeCombination(KeyCode.DIGIT4,
                KeyCombination.ALT_ANY), () -> tabPane.getSelectionModel().select(reportTab));
    }
}
