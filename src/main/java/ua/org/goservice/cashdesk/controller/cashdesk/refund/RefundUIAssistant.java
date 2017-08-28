package ua.org.goservice.cashdesk.controller.cashdesk.refund;

import javafx.beans.property.StringProperty;

public class RefundUIAssistant {

    private StringProperty checkNumber;
    private StringProperty dataOf;
    private StringProperty checkSum;
    private StringProperty cardNumber;
    private StringProperty cardType;
    private StringProperty checkSumToPay;
    private StringProperty cashFund;
    private StringProperty terminalFund;
    private StringProperty bonusFund;
    private StringProperty refundCash;
    private StringProperty refundBonuses;
    private StringProperty totalRefundSum;

    void indicateObservables(StringProperty checkNumber,
                                    StringProperty dataOf,
                                    StringProperty checkSum,
                                    StringProperty cardNumber,
                                    StringProperty cardType,
                                    StringProperty checkSumToPay,
                                    StringProperty cashFund,
                                    StringProperty terminalFund,
                                    StringProperty bonusFund,
                                    StringProperty refundCash,
                                    StringProperty refundBonuses,
                                    StringProperty totalRefundSum) {
        this.checkNumber = checkNumber;
        this.dataOf = dataOf;
        this.checkSum = checkSum;
        this.cardNumber = cardNumber;
        this.cardType = cardType;
        this.checkSumToPay = checkSumToPay;
        this.cashFund = cashFund;
        this.terminalFund = terminalFund;
        this.bonusFund = bonusFund;
        this.refundCash = refundCash;
        this.refundBonuses = refundBonuses;
        this.totalRefundSum = totalRefundSum;
    }

    void clearUI() {
        checkNumber.setValue(null);
        dataOf.setValue(null);
        checkSum.setValue(null);
        cardNumber.setValue(null);
        cardType.setValue(null);
        checkSumToPay.setValue(null);
        cashFund.setValue(null);
        terminalFund.setValue(null);
        bonusFund.setValue(null);
        refundCash.setValue(null);
        refundBonuses.setValue(null);
        totalRefundSum.setValue(null);
    }

    public void setCheckNumber(String checkNumber) {
        this.checkNumber.set(checkNumber);
    }

    public void setDataOf(String dataOf) {
        this.dataOf.set(dataOf);
    }

    public void setCheckSum(String checkSum) {
        this.checkSum.set(checkSum);
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber.set(cardNumber);
    }

    public void setCardType(String cardType) {
        this.cardType.set(cardType);
    }

    public void setCheckSumToPay(String checkSumToPay) {
        this.checkSumToPay.set(checkSumToPay);
    }

    public void setCashFund(String cashFund) {
        this.cashFund.set(cashFund);
    }

    public void setTerminalFund(String terminalFund) {
        this.terminalFund.set(terminalFund);
    }

    public void setBonusFund(String bonusFund) {
        this.bonusFund.set(bonusFund);
    }

    public void setRefundBonuses(String refundBonuses) {
        this.refundBonuses.set(refundBonuses);
    }

    public void setRefundCash(String refundCash) {
        this.refundCash.set(refundCash);
    }

    public void setTotalRefundSum(String totalRefundSum) {
        this.totalRefundSum.set(totalRefundSum);
    }
}
