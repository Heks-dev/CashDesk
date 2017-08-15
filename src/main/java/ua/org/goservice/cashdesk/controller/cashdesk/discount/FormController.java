package ua.org.goservice.cashdesk.controller.cashdesk.discount;

public class FormController {

    private boolean validCardNumber;
    private boolean validDiscountRate;
    private boolean validCardType;
    private boolean validName;
    private boolean validPhone;

    public void setValidCardNumber(boolean validCardNumber) {
        this.validCardNumber = validCardNumber;
    }

    void setValidDiscountRate(boolean validDiscountRate) {
        this.validDiscountRate = validDiscountRate;
    }

    void setValidCardType(boolean validCardType) {
        this.validCardType = validCardType;
    }

    public void setValidName(boolean validName) {
        this.validName = validName;
    }

    public void setValidPhone(boolean validPhone) {
        this.validPhone = validPhone;
    }

    boolean isValidForm() {
        return validCardNumber
                && validDiscountRate
                && validCardType
                && validName
                && validPhone;
    }
}
