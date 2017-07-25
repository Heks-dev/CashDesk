package ua.org.goservice.cashdesk.controller.cashdesk.sale;

import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import ua.org.goservice.cashdesk.controller.control.Alert;
import ua.org.goservice.cashdesk.controller.control.AlertHeader;
import ua.org.goservice.cashdesk.model.util.Validator;

import java.math.BigDecimal;

public class ProductCountDialogController {
    static final String LOCATION = "/view/cashdesk/productCountDialog.fxml";
    static final String TITLE = "Указать количество";
    private static final String COMMA_SEPARATOR = ".+(,).+";
    @FXML
    private JFXTextField countField;
    @FXML
    private Label availableCount;

    private Stage dialog;
    private Validator<String> validator;
    private BigDecimal desiredCount;
    private boolean isConfirmed = false;

    @FXML
    private void handleConfirm() {
        try {
            String input = countField.getText();
            validator.validate(input);
            setDesiredCount(input);
            isConfirmed = true;
            dialog.close();
        } catch (IllegalArgumentException e) {
            new Alert().callAlert(AlertHeader.PRODUCT_COUNT_VALIDATION, e.getMessage());
        } finally {
            countField.setText(null);
        }
    }

    private void setDesiredCount(String input) {
        if (input.matches(COMMA_SEPARATOR)) {
            input = input.replace(",", ".");
        }
        desiredCount = new BigDecimal(input);
    }

    public BigDecimal getDesiredCount() {
        return desiredCount;
    }

    public boolean isConfirmed() {
        return isConfirmed;
    }

    void setDependencies(Stage dialog, Validator<String> validator, BigDecimal available) {
        this.dialog = dialog;
        this.validator = validator;
        availableCount.setText(available.toString());
    }
}
