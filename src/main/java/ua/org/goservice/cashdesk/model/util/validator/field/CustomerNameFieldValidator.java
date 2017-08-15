package ua.org.goservice.cashdesk.model.util.validator.field;

import com.jfoenix.validation.base.ValidatorBase;
import javafx.scene.control.TextInputControl;
import ua.org.goservice.cashdesk.controller.cashdesk.discount.FormController;

public class CustomerNameFieldValidator extends ValidatorBase {
    private static final String CAP_LETTER_CONVENTION = "[A-ZА-Я]";
    private static final String NAME_CONVENTION = "^[A-ZА-Я][a-zа-я]+$";
    private static final String ERROR_MESSAGE = "Поле поддерживет только буквы";
    private static final String CAPITAL_LETTER_ERR_MSG = "Текст должен начинаться с заглавной буквы";
    private final FormController formController;

    public CustomerNameFieldValidator(FormController formController) {
        this.formController = formController;
    }

    @Override
    protected void eval() {
        if (srcControl.get() instanceof TextInputControl) {
            evalTextInput();
        }
    }

    private void evalTextInput() {
        TextInputControl inputControl = (TextInputControl) srcControl.get();
        boolean isEmptyField = inputControl.getText() == null || inputControl.getText().length() == 0;
        if (isEmptyField) {
            setVisible(false);
            formController.setValidName(false);
        } else {
            if (inputControl.getText().matches(NAME_CONVENTION)) {
                hasErrors.set(false);
                formController.setValidName(true);
            } else {
                defineErrorMessage(inputControl);
                hasErrors.set(true);
                formController.setValidName(false);
            }
        }
    }

    private void defineErrorMessage(TextInputControl inputControl) {
        if (!inputControl.getText().matches(CAP_LETTER_CONVENTION)) {
            setMessage(CAPITAL_LETTER_ERR_MSG);
        } else {
            setMessage(ERROR_MESSAGE);
        }
    }
}
