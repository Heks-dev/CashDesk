package ua.org.goservice.cashdesk.controller.dialogs.dialog;

import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import ua.org.goservice.cashdesk.controller.dialogs.Alerts;
import ua.org.goservice.cashdesk.controller.dialogs.alert.AlertCause;
import ua.org.goservice.cashdesk.model.util.validator.ProductCountValidator;

public class ProductCountDialogController {
    public static final String LOCATION = "/view/cashdesk/dialog/productCountDialog.fxml";
    public static final String TITLE = "Указать количество";
    @FXML
    private JFXTextField countField;
    @FXML
    private Label availableCount;

    private Stage dialog;
    private ProductCountValidator validator;
    private boolean isConfirmed = false;

    @FXML
    private void handleConfirm() {
        try {
            if (countField.getText() == null || countField.getText().length() == 0) return;
            String input = countField.getText();
            validator.validate(input);
            isConfirmed = true;
            dialog.close();
        } catch (IllegalArgumentException e) {
            AlertCause cause = AlertCause.INVALID_FORMAT;
            cause.setContent(e.getMessage());
            Alerts.notifying(dialog, cause);
        } finally {
            countField.setText(null);
        }
    }

    public boolean isConfirmed() {
        return isConfirmed;
    }

    public void setDependencies(Stage dialog, ProductCountValidator validator) {
        this.dialog = dialog;
        this.validator = validator;
        availableCount.setText(validator.getAvailable().toString());
        bindKeyClose(dialog);
    }

    private void bindKeyClose(Stage dialog) {
        dialog.getScene().setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                dialog.close();
            }
        });
    }
}
