package ua.org.goservice.cashdesk.controller.cashdesk;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import ua.org.goservice.cashdesk.MainApp;
import ua.org.goservice.cashdesk.controller.auth.ScreenLocker;
import ua.org.goservice.cashdesk.controller.cashdesk.sale.SaleController;
import ua.org.goservice.cashdesk.model.discount.DiscountCardManager;
import ua.org.goservice.cashdesk.model.employee.Employee;
import ua.org.goservice.cashdesk.model.organization.OrganizationManager;
import ua.org.goservice.cashdesk.model.warehouse.Warehouse;

import java.io.IOException;

public class CashDeskManager {

    private final Stage primaryStage;
    private final MainApp app;
    private final ScreenLocker screenLocker;

    public CashDeskManager(Stage primaryStage, MainApp app, ScreenLocker screenLocker) {
        this.primaryStage = primaryStage;
        this.app = app;
        this.screenLocker = screenLocker;
    }

    public void start(Employee employee) {
        OrganizationManager organizationManager = new OrganizationManager(employee.getOrgid());
        Warehouse warehouse = new Warehouse(employee.getStoreid(), organizationManager.getCurrentBuyer());
        DiscountCardManager discountCardManager = new DiscountCardManager();
        // load titled panes
        AnchorPane salePane = loadSalePane(organizationManager, warehouse, discountCardManager);
        // load root(titled panes)
        BorderPane root = loadRoot(salePane, organizationManager, warehouse);
        showCashDesk(root);
    }

    private void showCashDesk(BorderPane root) {
        primaryStage.setTitle(RootController.TITLE);
        primaryStage.setResizable(true);
        primaryStage.setFullScreen(true);
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/assets/css/cashdesk.css");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private BorderPane loadRoot(AnchorPane salePane, OrganizationManager organizationManager,
                                Warehouse warehouse) {
        BorderPane root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(RootController.LOCATION));
            root = loader.load();
            RootController controller = loader.getController();
            controller.setDependencies(salePane, this, organizationManager, warehouse, screenLocker);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return root;
    }

    private AnchorPane loadSalePane(OrganizationManager organizationManager, Warehouse warehouse,
                                    DiscountCardManager discountCardManager) {
        AnchorPane pane = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(SaleController.LOCATION));
            pane = loader.load();
            SaleController controller = loader.getController();
            controller.setDependencies(primaryStage, organizationManager, warehouse, discountCardManager);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pane;
    }

    void logOut() {
        try {
            primaryStage.close();
            primaryStage.setFullScreen(false);
            app.start(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}