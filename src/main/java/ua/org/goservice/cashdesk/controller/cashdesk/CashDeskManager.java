package ua.org.goservice.cashdesk.controller.cashdesk;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import ua.org.goservice.cashdesk.MainApp;
import ua.org.goservice.cashdesk.controller.auth.ScreenLocker;
import ua.org.goservice.cashdesk.controller.cashdesk.discount.IssueDiscountController;
import ua.org.goservice.cashdesk.controller.cashdesk.sale.SaleController;
import ua.org.goservice.cashdesk.model.discount.DiscountCardManager;
import ua.org.goservice.cashdesk.model.employee.Employee;
import ua.org.goservice.cashdesk.model.organization.OrganizationManager;
import ua.org.goservice.cashdesk.model.trade.SaleOperator;
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
        // load panes
        FXMLLoader saleLoader = loadSalePane(organizationManager, warehouse, discountCardManager);
        FXMLLoader discountLoader = loadDiscountPane(discountCardManager);
        // load root
        loadRoot(saleLoader, discountLoader, organizationManager, warehouse);
        showCashDesk();
    }

    private FXMLLoader loadSalePane(OrganizationManager organizationManager, Warehouse warehouse,
                                    DiscountCardManager discountCardManager) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(SaleController.LOCATION));
        try {
            loader.load();
            SaleController saleController = loader.getController();
            saleController.setDependencies(primaryStage, organizationManager, warehouse,
                    discountCardManager, new SaleOperator());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return loader;
    }

    private FXMLLoader loadDiscountPane(DiscountCardManager discountCardManager) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(IssueDiscountController.LOCATION));
        try {
            loader.load();
            IssueDiscountController controller = loader.getController();
            controller.setDependencies(primaryStage, discountCardManager);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return loader;
    }

    private void loadRoot(FXMLLoader saleLoader, FXMLLoader discountLoader, OrganizationManager organizationManager,
                          Warehouse warehouse) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(RootController.LOCATION));
            BorderPane root = loader.load();
            Scene scene = new Scene(root);
            scene.getStylesheets().add("/assets/css/main.css");
            primaryStage.setScene(scene);
            RootController controller = loader.getController();
            controller.setDependencies(primaryStage, saleLoader, discountLoader,
                    this, organizationManager, warehouse, screenLocker, scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showCashDesk() {
        primaryStage.setTitle(RootController.TITLE);
        primaryStage.setResizable(true);
        primaryStage.setFullScreen(true);
        primaryStage.show();
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