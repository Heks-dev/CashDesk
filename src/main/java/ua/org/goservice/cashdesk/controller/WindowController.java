package ua.org.goservice.cashdesk.controller;

import javafx.stage.Stage;
import ua.org.goservice.cashdesk.controller.auth.AuthCaller;
import ua.org.goservice.cashdesk.controller.auth.AuthSceneAssistant;
import ua.org.goservice.cashdesk.model.employee.Employee;

public class WindowController implements Launcher, Authorizable {

    private final Stage primaryStage;
    private final AuthCaller authCaller;

    public WindowController(Stage primaryStage) {
        this.primaryStage = primaryStage;
        authCaller = new AuthSceneAssistant(this, primaryStage);
    }

    @Override
    public void launch() {
        authCaller.signIn();
    }

    @Override
    public void authorize() {
        Employee employee = authCaller.getEmployee();
    }

    private boolean isNotEmptyPrimaryStage() {
        return primaryStage.getScene() != null;
    }
}
