package ua.org.goservice.cashdesk.controller.auth;

import com.jfoenix.controls.JFXPasswordField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import ua.org.goservice.cashdesk.model.exception.AuthorizationFailedException;
import ua.org.goservice.cashdesk.model.util.Validator;

import java.net.URL;
import java.util.ResourceBundle;

public class AuthorizationController implements Initializable {
    static final String LOCATION = "/view/authorization.fxml";
    static final String TITLE = "Авторизация в системе";
    @FXML
    private JFXPasswordField passwordField;
    @FXML
    private Text errorMessageText;

    private AuthorizationManager authorizationManager;
    private Validator<String> validator;

    @FXML
    private void handleSignIn() {
        if (errorMessageText.isVisible()) invisibleErrorField();
        if (passwordField.getText().isEmpty()) return;
        try {
            validator.validate(passwordField.getText());
            authorizationManager.login(passwordField.getText());
        } catch (AuthorizationFailedException e) {
            errorMessageText.setText(e.getMessage());
            errorMessageText.setVisible(true);
        }
    }

    void setDependencies(AuthorizationManager authorizationManager, Validator<String> validator) {
        this.authorizationManager = authorizationManager;
        this.validator = validator;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        invisibleErrorField();
    }

    private void invisibleErrorField() {
        errorMessageText.setVisible(false);
    }
}
