package ua.org.goservice.cashdesk.model.util.validator.field;

import com.jfoenix.validation.base.ValidatorBase;
import javafx.scene.Node;
import javafx.scene.control.TextInputControl;

public class CheckNumberFieldValidator extends ValidatorBase {

    private static final String NUMERIC_EXP = "\\d+";
    private static final String ERR_MESSAGE = "Поле поддерживает только цифры.";

    public CheckNumberFieldValidator() {
        setMessage(ERR_MESSAGE);
    }

    @Override
    protected void eval() {
        Node control = srcControl.get();
        if (control instanceof TextInputControl) {
            evalInputField((TextInputControl)control);
        }
    }

    private void evalInputField(TextInputControl control) {
        String input = control.getText();
        boolean emptyField = input == null || input.length() == 0;
        if (emptyField) {
            hasErrors.set(false);
        } else {
            if (input.matches(NUMERIC_EXP)) {
                hasErrors.set(false);
            } else {
                hasErrors.set(true);
            }
        }
    }
}
