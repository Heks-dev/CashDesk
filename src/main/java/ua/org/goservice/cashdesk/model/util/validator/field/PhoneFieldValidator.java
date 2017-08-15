package ua.org.goservice.cashdesk.model.util.validator.field;

import com.jfoenix.validation.base.ValidatorBase;
import javafx.scene.control.TextInputControl;
import ua.org.goservice.cashdesk.controller.cashdesk.discount.FormController;

public class PhoneFieldValidator extends ValidatorBase {
    private static final String PHONE_CONVENTION = "^\\+?38(\\d){3}(\\d){7}$";
    private static final String ERR_MESSAGE = "Поле должно начинаться с +38";
    private final FormController formController;

    public PhoneFieldValidator(FormController formController) {
        this.formController = formController;
        setMessage(ERR_MESSAGE);
    }

    @Override
    protected void eval() {
        if (getSrcControl() instanceof TextInputControl) {
            evalInputField();
        }
    }

    private void evalInputField() {
        TextInputControl inputControl = (TextInputControl) getSrcControl();
        boolean emptyField = inputControl.getText() == null || inputControl.getText().length() == 0;
        if (emptyField) {
            setVisible(false);
            formController.setValidPhone(false);
        } else {
            if (inputControl.getText().matches(PHONE_CONVENTION)) {
                hasErrors.set(false);
                formController.setValidPhone(true);
            } else {
                hasErrors.set(true);
                formController.setValidPhone(false);
            }
        }
    }
}
