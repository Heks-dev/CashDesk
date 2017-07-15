package ua.org.goservice.cashdesk.controller.auth;

import com.jfoenix.controls.JFXPasswordField;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import ua.org.goservice.cashdesk.model.employee.Employee;
import ua.org.goservice.cashdesk.model.exception.ActionDeniedException;
import ua.org.goservice.cashdesk.model.exception.Exceptions;

public class SignInController {
    private static final String PASSWORD_EXP = "[^\\W_]{5,16}";
    @FXML
    private JFXPasswordField passwordField;
    @FXML
    private Text errorMessageText;

    private String provenPass;

    @FXML
    private void handleSignIn() {
        if (errorMessageText.isVisible()) errorMessageText.setVisible(false);
        if (passwordField.getText().isEmpty()) return;
        try {
            parsePassword();
            Employee employee = new Employee();
            employee.loadData(passwordField.getText());
        } catch (ActionDeniedException e) {
            activateErrorMessage(e);
        }
    }

    private void parsePassword() {
        if (!passwordField.getText().matches(PASSWORD_EXP)) {
            throw new ActionDeniedException(Exceptions.WRONG_PASSWORD_FORMAT);
        }
    }

    private void activateErrorMessage(ActionDeniedException e) {
        errorMessageText.setText(e.getMessage());
        errorMessageText.setVisible(true);
    }

    @FXML
    private void initialize() {
        errorMessageText.setVisible(false);
    }
}
