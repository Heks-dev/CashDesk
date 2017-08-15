package ua.org.goservice.cashdesk.controller.cashdesk.discount;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import ua.org.goservice.cashdesk.controller.dialogs.Alerts;
import ua.org.goservice.cashdesk.controller.dialogs.alert.AlertCause;
import ua.org.goservice.cashdesk.model.discount.DiscountCard;
import ua.org.goservice.cashdesk.model.discount.DiscountCardManager;
import ua.org.goservice.cashdesk.model.exception.ActionDeniedException;
import ua.org.goservice.cashdesk.model.exception.Exceptions;
import ua.org.goservice.cashdesk.model.exception.NotFoundException;
import ua.org.goservice.cashdesk.model.util.validator.field.CardNumberFieldValidator;
import ua.org.goservice.cashdesk.model.util.validator.field.CustomerNameFieldValidator;
import ua.org.goservice.cashdesk.model.util.validator.field.PhoneFieldValidator;

import java.net.URL;
import java.util.ResourceBundle;

public class IssueDiscountController implements Initializable {
    public static final String LOCATION = "/view/cashdesk/issueDiscount.fxml";
    @FXML
    private JFXTextField cardNumber;
    @FXML
    private JFXComboBox<Integer> discountRateBox;
    @FXML
    private JFXRadioButton discountType;
    @FXML
    private JFXRadioButton accumulativeType;
    @FXML
    private JFXTextField customerName;
    @FXML
    private JFXTextField customerSurname;
    @FXML
    private JFXTextField phoneNumber;

    private ToggleGroup cardTypeGroup;
    /**
     * Model objects
     */
    private FormController formController = new FormController();
    private DiscountCardManager cardManager;
    private Stage stage;

    @FXML
    private void clear() {
        clearFields();
    }

    @FXML
    private void submit() {
        boolean allFieldsFilled = formController.isValidForm();
        if (allFieldsFilled) {
            try {
                if (isCardOccupied()) throw new ActionDeniedException(Exceptions.CARD_ALREADY_EXISTS);
                Integer cardRate = discountRateBox.getSelectionModel().getSelectedItem();
                String cardType = (String) cardTypeGroup.getSelectedToggle().getUserData();
                cardManager.issueCard(Long.valueOf(cardNumber.getText()),
                        cardRate, cardType, customerName.getText(),
                        customerSurname.getText(), phoneNumber.getText());
                Alerts.notifying(stage, AlertCause.CARD_ISSUED);
                clearFields();
            } catch (ActionDeniedException e) {
                AlertCause cause = AlertCause.ACTION_DENIED;
                cause.setContent(e.getMessage());
                Alerts.notifying(stage, cause);
            }
        }
    }

    private boolean isCardOccupied() {
        try {
            DiscountCard card = cardManager.searchDiscountByNumber(Long.parseLong(cardNumber.getText()));
            return card != null;
        } catch (NotFoundException e) {
            return false;
        }
    }

    private void clearFields() {
        cardNumber.setText(null);
        discountRateBox.setValue(null);
        resetTypeGroup();
        customerName.setText(null);
        customerSurname.setText(null);
        phoneNumber.setText(null);
    }

    private void resetTypeGroup() {
        if (cardTypeGroup.getSelectedToggle() != null) {
            cardTypeGroup.getSelectedToggle().setSelected(false);
        }
    }

    public void setDependencies(Stage stage, DiscountCardManager cardManager) {
        this.stage = stage;
        this.cardManager = cardManager;
        initializeRateBox();
    }

    private void initializeRateBox() {
        discountRateBox.setItems(cardManager.getRates());
        discountRateBox.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                formController.setValidDiscountRate(true);
            } else {
                formController.setValidDiscountRate(false);
            }
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeCardTypeGroup();
        initializeFields();
    }

    private void initializeCardTypeGroup() {
        cardTypeGroup = new ToggleGroup();
        discountType.setUserData(DiscountCard.DISCOUNT_TYPE);
        accumulativeType.setUserData(DiscountCard.ACCUMULATIVE_TYPE);
        cardTypeGroup.getToggles().add(discountType);
        cardTypeGroup.getToggles().add(accumulativeType);
        cardTypeGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                formController.setValidCardType(true);
            } else {
                formController.setValidCardType(false);
            }
        });
    }

    private void initializeFields() {
        initializeDiscountNumberField();
        initializeNameField();
        initializeSurnameField();
        initializePhoneField();
    }

    private void initializeDiscountNumberField() {
        CardNumberFieldValidator validator = new CardNumberFieldValidator(formController);
        validator.setMessage(Exceptions.INVALID_DISCOUNT_CARD_NUMBER_FORMAT);
        cardNumber.getValidators().add(validator);
        cardNumber.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                boolean invalidInput = cardNumber.validate();
                if (invalidInput) {
                    validator.setVisible(true);
                } else {
                    validator.setVisible(false);
                }
            }
        });
    }

    private void initializeNameField() {
        CustomerNameFieldValidator validator = new CustomerNameFieldValidator(formController);
        customerName.getValidators().add(validator);
        customerName.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                boolean invalidInput = customerName.validate();
                if (invalidInput) {
                    validator.setVisible(true);
                } else {
                    validator.setVisible(false);
                }
            }
        });
    }

    private void initializeSurnameField() {
        CustomerNameFieldValidator validator = new CustomerNameFieldValidator(formController);
        customerSurname.getValidators().add(validator);
        customerSurname.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                boolean invalidInput = customerSurname.validate();
                if (invalidInput) {
                    validator.setVisible(true);
                } else {
                    validator.setVisible(false);
                }
            }
        });
    }

    private void initializePhoneField() {
        PhoneFieldValidator validator = new PhoneFieldValidator(formController);
        phoneNumber.getValidators().add(validator);
        phoneNumber.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                boolean invalidInput = phoneNumber.validate();
                if (invalidInput) {
                    validator.setVisible(true);
                } else {
                    validator.setVisible(false);
                }
            }
        });
    }
}
