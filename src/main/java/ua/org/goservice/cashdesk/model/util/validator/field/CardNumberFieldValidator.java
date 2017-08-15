package ua.org.goservice.cashdesk.model.util.validator.field;

import com.jfoenix.validation.base.ValidatorBase;
import javafx.scene.control.TextInputControl;
import ua.org.goservice.cashdesk.controller.cashdesk.discount.FormController;
import ua.org.goservice.cashdesk.model.util.validator.DiscountNumberValidator;

public class CardNumberFieldValidator extends ValidatorBase {

    private DiscountNumberValidator numberValidator = new DiscountNumberValidator();
    private final FormController formController;

    public CardNumberFieldValidator(FormController formController) {
        this.formController = formController;
    }

    @Override
    protected void eval() {
        if (srcControl.get() instanceof TextInputControl) {
            evalInputField();
        }
    }

    private void evalInputField() {
        TextInputControl inputControl = (TextInputControl) srcControl.get();
        boolean isEmptyField = inputControl.getText() == null || inputControl.getText().length() == 0;
        if (isEmptyField) {
            hasErrors.set(false);
            formController.setValidCardNumber(false);
        } else {
            try {
                numberValidator.validate(inputControl.getText());
                hasErrors.set(false);
                formController.setValidCardNumber(true);
            } catch (IllegalArgumentException e) {
                hasErrors.set(true);
                formController.setValidCardNumber(false);
            }
        }
    }
}
