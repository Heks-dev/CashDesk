package ua.org.goservice.cashdesk.controller;

import javafx.stage.Stage;
import ua.org.goservice.cashdesk.controller.auth.AuthSceneAssistant;

public class WindowController implements Launcher {

    private final Stage primaryStage;
    private final AuthSceneAssistant authSceneAssistant;

    public WindowController(Stage primaryStage) {
        this.primaryStage = primaryStage;
        authSceneAssistant = new AuthSceneAssistant(primaryStage);
    }

    @Override
    public void launch() {
        callSignIn();
    }

    private void callSignIn() {
        authSceneAssistant.authorize();
    }

    private boolean isNotEmptyPrimaryStage() {
        return primaryStage.getScene() != null;
    }
}
