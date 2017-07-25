package ua.org.goservice.cashdesk.controller.cashdesk;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import ua.org.goservice.cashdesk.MainApp;
import ua.org.goservice.cashdesk.controller.auth.ScreenBlocker;
import ua.org.goservice.cashdesk.controller.cashdesk.sale.SaleController;
import ua.org.goservice.cashdesk.model.employee.Employee;

import java.io.IOException;

public class CashDeskManager {

    private final Stage primaryStage;
    private final MainApp app;
    private final ScreenBlocker screenBlocker;

    private BorderPane root;

    public CashDeskManager(Stage primaryStage, MainApp app, ScreenBlocker screenBlocker) {
        this.primaryStage = primaryStage;
        this.app = app;
        this.screenBlocker = screenBlocker;
    }

    public void startCashDesk(Employee employee) {
        loadRoot(employee);
        if (primaryStage.getScene() != null) {
            primaryStage.close();
        }
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    private void loadRoot(Employee employee) {
        try {
            FXMLLoader rootLoader = new FXMLLoader(getClass().getResource(RootController.LOCATION));
            root = rootLoader.load();
            RootController controller = rootLoader.getController();
            controller.setDependencies(primaryStage, screenBlocker, employee);
            primaryStage.setTitle(RootController.TITLE);
            primaryStage.setResizable(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}