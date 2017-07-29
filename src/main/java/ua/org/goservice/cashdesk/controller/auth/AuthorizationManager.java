package ua.org.goservice.cashdesk.controller.auth;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import ua.org.goservice.cashdesk.controller.Continuer;
import ua.org.goservice.cashdesk.model.employee.Employee;

import java.io.IOException;

public class AuthorizationManager implements ScreenLocker {

    private final PasswordValidator validator = new PasswordValidator();
    private final Stage primaryStage;
    private final Continuer continuer;

    public AuthorizationManager(Stage primaryStage, Continuer continuer) {
        this.primaryStage = primaryStage;
        this.continuer = continuer;
    }

    public void startSignIn() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(AuthorizationController.LOCATION));
            AnchorPane root = loader.load();
            AuthorizationController controller = loader.getController();
            controller.setDependencies(this, validator);
            primaryStage.setScene(new Scene(root));
            primaryStage.setResizable(false);
            primaryStage.setTitle(AuthorizationController.TITLE);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void authorize() {
        primaryStage.close();
        continuer.followUp(new Employee(validator.extract()));
    }

    @Override
    public void lockScreen() {

    }
}
