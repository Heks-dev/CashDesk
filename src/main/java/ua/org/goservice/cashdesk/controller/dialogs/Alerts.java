package ua.org.goservice.cashdesk.controller.dialogs;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import ua.org.goservice.cashdesk.controller.dialogs.alert.AlertCause;
import ua.org.goservice.cashdesk.controller.dialogs.alert.AlertController;
import ua.org.goservice.cashdesk.controller.dialogs.alert.ConfirmationAlertController;
import ua.org.goservice.cashdesk.model.exception.CancelOperationException;

import java.io.IOException;

public class Alerts {

    public static boolean confirm(Window owner, AlertCause cause) {
        try {
            FXMLLoader loader = new FXMLLoader(Class.class.getResource(ConfirmationAlertController.LOCATION));
            AnchorPane root = loader.load();
            Stage dialog = createStage(root, owner);
            ConfirmationAlertController controller = loader.getController();
            controller.setDependencies(dialog, cause);
            dialog.showAndWait();
            return controller.isConfirmed();
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new CancelOperationException();
    }

    // todo trying to compile with window
    public static void notifying(Window owner, AlertCause cause) {
        try {
            FXMLLoader loader = new FXMLLoader(Class.class.getResource(AlertController.LOCATION));
            AnchorPane root = loader.load();
            Stage dialog = createStage(root, owner);
            AlertController controller = loader.getController();
            controller.setDependencies(dialog, cause);
            dialog.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Stage createStage(AnchorPane root, Window owner) {
        Stage stage = new Stage();
        stage.setScene(createScene(root));
        stage.setResizable(false);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(owner);
        stage.initStyle(StageStyle.UNDECORATED);
        return stage;
    }

    private static Scene createScene(AnchorPane root) {
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/assets/css/main.css");
        return scene;
    }
}
