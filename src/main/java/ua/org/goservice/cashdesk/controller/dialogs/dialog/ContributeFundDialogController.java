package ua.org.goservice.cashdesk.controller.dialogs.dialog;

import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import ua.org.goservice.cashdesk.controller.dialogs.Alerts;
import ua.org.goservice.cashdesk.controller.dialogs.alert.AlertCause;
import ua.org.goservice.cashdesk.model.util.validator.fund.FundValidator;
import ua.org.goservice.cashdesk.model.draft.PaymentMethod;

public class ContributeFundDialogController {
    public static final String LOCATION = "/view/cashdesk/dialog/contributeFundDialog.fxml";
    public static final String TITLE = "Внесение средств";
    @FXML
    private JFXTextField amountField;
    @FXML
    private Label fundTypeLabel;

    private Stage dialog;
    private FundValidator validator;
    private boolean confirmed = false;
    @FXML
    private void handleConfirm() {
        if (amountField.getText().length() == 0) return;
        try {
            validator.validate(amountField.getText());
            confirmed = true;
            dialog.close();
        } catch (IllegalArgumentException e) {
            AlertCause cause = AlertCause.ACTION_DENIED;
            cause.setContent(e.getMessage());
            Alerts.notifying(dialog, cause);
        }
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setDependencies(Stage dialog, FundValidator validator, PaymentMethod paymentMethod) {
        this.dialog = dialog;
        this.validator = validator;
        fundTypeLabel.setText(paymentMethod.getValue());
    }
}
