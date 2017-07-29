package ua.org.goservice.cashdesk.controller.cashdesk.sale;

import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import ua.org.goservice.cashdesk.model.util.Validator;

public class DiscountNumberDialogController {
    static final String LOCATION = "/view/cashdesk/sale/discountNumberDialog.fxml";
    static final String TITLE = "Считать дисконт";
    @FXML
    private JFXTextField discountNumberField;

    private Stage dialog;
    private Validator<String> validator;

    private boolean confirm = false;
    private Long cardNumber;
    @FXML
    private void handleConfirm() {
        try {
            if (discountNumberField.getText().length() == 0) return;
            validator.validate(discountNumberField.getText());
            cardNumber = Long.parseLong(discountNumberField.getText());
            confirm = true;
            dialog.close();
        } catch (IllegalArgumentException e) {
            // todo alert
        }
    }

    public boolean isConfirm() {
        return confirm;
    }

    public Long getCardNumber() {
        return cardNumber;
    }

    void setDependencies(Stage dialog, Validator<String> validator) {
        this.dialog = dialog;
        this.validator = validator;
    }
}
