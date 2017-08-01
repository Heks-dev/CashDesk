package ua.org.goservice.cashdesk.controller.dialogs.alert;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AlertController implements Initializable {
    public static final String LOCATION = "/view/cashdesk/alert/alert.fxml";
    @FXML
    private Label title;
    @FXML
    private Text content;
    private Stage dialog;

    @FXML
    private void handleClose() {
        dialog.close();
    }

    public void setDependencies(Stage dialog, AlertCause cause) {
        this.dialog = dialog;
        this.title.setText(cause.getTitle());
        this.content.setText(cause.getContent());
        bindKeyClose();
    }

    private void bindKeyClose() {
        dialog.getScene().setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE || event.getCode() == KeyCode.ENTER) {
                dialog.close();
            }
        });
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        title.setText(null);
        content.setText(null);
    }
}
