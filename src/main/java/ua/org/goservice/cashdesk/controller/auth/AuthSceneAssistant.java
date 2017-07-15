package ua.org.goservice.cashdesk.controller.auth;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class AuthSceneAssistant implements Authorizable, Lockable {

    private static final String SIGN_IN_LOCATION = "/view/signIn.fxml";
    private static final String SIGN_IN_TITLE = "Авторизация";
    private final Stage primaryStage;

    private Scene signInScene;

    public AuthSceneAssistant(Stage primaryStage) {
        this.primaryStage = primaryStage;
        loadSignInScene();
    }

    @Override
    public void authorize() {
        primaryStage.setScene(signInScene);
        primaryStage.setTitle(SIGN_IN_TITLE);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    @Override
    public void lock() {
        // todo
    }

    private void loadSignInScene() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(SIGN_IN_LOCATION));
            AnchorPane root = loader.load();
            signInScene = new Scene(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
