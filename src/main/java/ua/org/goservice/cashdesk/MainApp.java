package ua.org.goservice.cashdesk;

import javafx.application.Application;
import javafx.stage.Stage;
import ua.org.goservice.cashdesk.controller.Continuer;
import ua.org.goservice.cashdesk.controller.auth.AuthorizationManager;
import ua.org.goservice.cashdesk.controller.cashdesk.CashDeskManager;
import ua.org.goservice.cashdesk.model.employee.Employee;

public class MainApp extends Application implements Continuer {

    private CashDeskManager deskManager;

    @Override
    public void start(Stage primaryStage) throws Exception {
        AuthorizationManager authManager = new AuthorizationManager(primaryStage, this);
        deskManager = new CashDeskManager(primaryStage, this, authManager);
        authManager.startSignIn();
    }

    @Override
    public void followUp(Employee employee) {
        deskManager.startCashDesk(employee);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
