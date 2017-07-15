package ua.org.goservice.cashdesk;

import javafx.application.Application;
import javafx.stage.Stage;
import ua.org.goservice.cashdesk.controller.Launcher;
import ua.org.goservice.cashdesk.controller.WindowController;

public class MainApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Launcher launcher = new WindowController(primaryStage);
        launcher.launch();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
