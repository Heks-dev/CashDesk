package ua.org.goservice.cashdesk.controller.auth;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import ua.org.goservice.cashdesk.controller.Continuer;
import ua.org.goservice.cashdesk.model.employee.Employee;
import ua.org.goservice.cashdesk.model.util.Validator;

import java.io.IOException;

public class AuthorizationManager implements ScreenBlocker {

    private final Validator<String> validator = new PasswordValidator();
    private final Stage primaryStage;
    private final Continuer continuer;
    private Scene authorizationScene;

    private String password;

    public AuthorizationManager(Stage primaryStage, Continuer continuer) {
        this.primaryStage = primaryStage;
        this.continuer = continuer;
        loadAuthScene();
    }

    private void loadAuthScene() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(AuthorizationController.LOCATION));
            AnchorPane root = loader.load();
            AuthorizationController controller = loader.getController();
            controller.setDependencies(this, validator);
            authorizationScene = new Scene(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startSignIn() {
        primaryStage.setScene(authorizationScene);
        primaryStage.setResizable(false);
        primaryStage.setTitle(AuthorizationController.TITLE);
        primaryStage.show();
    }

    void login(String password) {
        Employee employee = new Employee(password);
        this.password = password;
        continuer.followUp(employee);
    }

    @Override
    public void blockScreen() {

    }
}
