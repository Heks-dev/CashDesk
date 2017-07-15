package ua.org.goservice.cashdesk.controller.auth;

import com.jfoenix.controls.JFXPasswordField;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import ua.org.goservice.cashdesk.model.exception.AuthorizationFailedException;
import ua.org.goservice.cashdesk.model.util.Validator;

public class SignInController {
    @FXML
    private JFXPasswordField passwordField;
    @FXML
    private Text errorMessageText;

    private Authorizable assistant;
    private Validator<String> validator;

    @FXML
    private void handleSignIn() {
        if (errorMessageText.isVisible()) errorMessageText.setVisible(false);
        if (passwordField.getText().isEmpty()) return;
        try {
            validator.validate(passwordField.getText());
            assistant.authorize();
        } catch (AuthorizationFailedException e) {
            activateErrorMessage(e);
        }
    }

    private void activateErrorMessage(AuthorizationFailedException e) {
        errorMessageText.setText(e.getMessage());
        errorMessageText.setVisible(true);
    }

    /**
     *  method initialize() - Используется загрузчиком FXMLLoader
     */
    @FXML
    public void initialize() {
        errorMessageText.setVisible(false);
    }

    void setDependencies(Authorizable assistant, Validator<String> validator) {
        this.assistant = assistant;
        this.validator = validator;
    }
}
