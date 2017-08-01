package ua.org.goservice.cashdesk.controller.auth;

import com.jfoenix.controls.JFXPasswordField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ua.org.goservice.cashdesk.model.exception.AuthorizationFailedException;

import java.net.URL;
import java.util.ResourceBundle;

public class LockScreenController implements Initializable {
    public static final String LOCATION = "/view/lockScreen.fxml";
    @FXML
    private JFXPasswordField passwordField;
    @FXML
    private Text errorMessage;

    private Stage dialog;
    private PasswordValidator validator;

    @FXML
    private void handleConfirm() {
        if (passwordField.getText() == null || passwordField.getText().length() == 0) return;
        initializeErrorMessage();
        try {
            validator.validateCheckedPassword(passwordField.getText());
            dialog.close();
        } catch (AuthorizationFailedException e) {
            errorMessage.setText(e.getMessage());
            errorMessage.setVisible(true);
        } finally {
            passwordField.setText(null);
        }
    }

    public void setDependencies(Stage dialog, PasswordValidator validator) {
        this.dialog = dialog;
        this.validator = validator;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        passwordField.setText(null);
        initializeErrorMessage();
    }

    private void initializeErrorMessage() {
        errorMessage.setText(null);
        errorMessage.setVisible(false);
    }
}
