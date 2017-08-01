package ua.org.goservice.cashdesk.controller.dialogs.dialog;

import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import ua.org.goservice.cashdesk.controller.dialogs.Alerts;
import ua.org.goservice.cashdesk.controller.dialogs.alert.AlertCause;
import ua.org.goservice.cashdesk.model.util.validator.Validator;

public class DiscountNumberDialogController {
    public static final String LOCATION = "/view/cashdesk/dialog/discountNumberDialog.fxml";
    public static final String TITLE = "Считать дисконт";
    @FXML
    private JFXTextField discountNumberField;

    private Stage dialog;
    private Validator<String> validator;

    private boolean confirm = false;
    @FXML
    private void handleConfirm() {
        if (discountNumberField.getText() == null
                || discountNumberField.getText().length() == 0) return;
        try {
            validator.validate(discountNumberField.getText());
            confirm = true;
            dialog.close();
        } catch (IllegalArgumentException e) {
            AlertCause cause = AlertCause.INVALID_FORMAT;
            cause.setContent(e.getMessage());
            Alerts.notifying(dialog, cause);
        }
    }

    public boolean isConfirm() {
        return confirm;
    }

    public void setDependencies(Stage dialog, Validator<String> validator) {
        this.dialog = dialog;
        this.validator = validator;
    }
}
