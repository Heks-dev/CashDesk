package ua.org.goservice.cashdesk.controller.auth;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import ua.org.goservice.cashdesk.controller.Authorizable;
import ua.org.goservice.cashdesk.model.employee.Employee;

import java.io.IOException;

public class AuthSceneAssistant implements Authorizable, AuthCaller {
    private final AuthValidator validator = new AuthValidator();
    private final Stage primaryStage;
    private final Authorizable windowController;
    private Scene signInScene;

    public AuthSceneAssistant(Authorizable windowController, Stage primaryStage) {
        this.windowController = windowController;
        this.primaryStage = primaryStage;
        loadSignInScene();
    }

    @Override
    public void authorize() {
        windowController.authorize();
    }

    @Override
    public void signIn() {
        primaryStage.setScene(signInScene);
        primaryStage.setTitle(SignInController.SIGN_IN_TITLE);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    @Override
    public void lockScreen() {

    }

    public Employee getEmployee() {
        return (Employee) validator.getLoadable();
    }

    private void loadSignInScene() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(SignInController.SIGN_IN_LOCATION));
            AnchorPane root = loader.load();
            SignInController signInController = loader.getController();
            signInController.setDependencies(this, validator);
            signInScene = new Scene(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
