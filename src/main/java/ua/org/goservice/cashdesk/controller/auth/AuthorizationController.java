package ua.org.goservice.cashdesk.controller.auth;

import com.jfoenix.controls.JFXPasswordField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import ua.org.goservice.cashdesk.model.api.ApiFilter;
import ua.org.goservice.cashdesk.model.api.ApiUrl;
import ua.org.goservice.cashdesk.model.api.ApiVal;
import ua.org.goservice.cashdesk.model.communication.HttpRequestExecutor;
import ua.org.goservice.cashdesk.model.communication.RequestExecutor;
import ua.org.goservice.cashdesk.model.communication.request.Filter;
import ua.org.goservice.cashdesk.model.communication.request.FilterSet;
import ua.org.goservice.cashdesk.model.communication.request.RequestBuilder;
import ua.org.goservice.cashdesk.model.exception.AuthorizationFailedException;
import ua.org.goservice.cashdesk.model.exception.CommunicationException;

import java.net.URL;
import java.util.ResourceBundle;

public class AuthorizationController implements Initializable {
    static final String LOCATION = "/view/authorization.fxml";
    static final String TITLE = "Авторизация в системе";
    @FXML
    private JFXPasswordField passwordField;
    @FXML
    private Text errorMessageText;

    private RequestExecutor requestExecutor = new HttpRequestExecutor();
    private PasswordValidator validator;
    private AuthorizationManager authorizationManager;

    @FXML
    private void handleSignIn() {
        if (errorMessageText.isVisible()) invisibleErrorField();
        if (passwordField.getText().isEmpty()) return;
        try {
            validator.validate(passwordField.getText());
            requestExecutor.sendRequest(new RequestBuilder(ApiUrl.AUTHORIZATION, ApiVal.AUTH,
                    new FilterSet(new Filter(ApiFilter.PASSWORD, passwordField.getText()))));
            validator.validateJson(requestExecutor.getResponse());
            authorizationManager.authorize();
        } catch (AuthorizationFailedException | CommunicationException e) {
            errorMessageText.setText(e.getMessage());
            errorMessageText.setVisible(true);
        }
    }

    void setDependencies(AuthorizationManager authorizationManager, PasswordValidator validator) {
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
