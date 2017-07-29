package ua.org.goservice.cashdesk.controller.cashdesk.sale;

import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import ua.org.goservice.cashdesk.controller.cashdesk.sale.validator.FundValidator;
import ua.org.goservice.cashdesk.model.util.Validator;

import java.math.BigDecimal;

public class ContributeFundDialogController {
    static final String LOCATION = "/view/cashdesk/sale/contributeFundDialog.fxml";
    static final String TITLE = "Внесение средств";
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
            System.out.println(e.getMessage());
            // todo alert
        }
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    void setDependencies(Stage dialog, FundValidator validator, String fundType) {
        this.dialog = dialog;
        this.validator = validator;
        fundTypeLabel.setText(fundType + ":");
    }
}
