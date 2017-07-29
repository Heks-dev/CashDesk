package ua.org.goservice.cashdesk.controller.cashdesk.sale;

import javafx.beans.property.StringProperty;

public class SaleUIAssistant {

    private StringProperty checkSum;
    private StringProperty discountRate;
    private StringProperty bonusesAccrued;

    private StringProperty contributedCashFund;
    private StringProperty contributedTerminalFund;
    private StringProperty contributedBonusFund;

    private StringProperty toPay;
    private StringProperty contributedTotalFunds;
    private StringProperty oddsType;
    private StringProperty odds;

    private StringProperty discountCardOwner;
    private StringProperty discountCardNumber;
    private StringProperty discountCardType;
    private StringProperty discountCardBalance;

    void indicateObservables(StringProperty checkSum, StringProperty discountRate,
                             StringProperty bonusesAccrued, StringProperty contributedCashFund,
                             StringProperty contributedTerminalFund, StringProperty contributedBonusFund,
                             StringProperty toPay, StringProperty contributedTotalFunds,
                             StringProperty oddsType, StringProperty odds,
                             StringProperty discountCardOwner, StringProperty discountCardNumber,
                             StringProperty discountCardType, StringProperty discountCardBalance) {
        this.checkSum = checkSum;
        this.discountRate = discountRate;
        this.bonusesAccrued = bonusesAccrued;
        this.contributedCashFund = contributedCashFund;
        this.contributedTerminalFund = contributedTerminalFund;
        this.contributedBonusFund = contributedBonusFund;
        this.contributedTotalFunds = contributedTotalFunds;
        this.toPay = toPay;
        this.oddsType = oddsType;
        this.odds = odds;
        this.discountCardOwner = discountCardOwner;
        this.discountCardNumber = discountCardNumber;
        this.discountCardType = discountCardType;
        this.discountCardBalance = discountCardBalance;
    }

    public void setCheckSum(String value) {
        checkSum.setValue(value);
    }

    public void setDiscountRate(String value) {
        discountRate.setValue(value);
    }

    public void setBonusesAccrued(String value) {
        bonusesAccrued.setValue(value);
    }

    public void setContributedCashFund(String value) {
        contributedCashFund.setValue(value);
    }

    public void setContributedTerminalFund(String value) {
        contributedTerminalFund.setValue(value);
    }

    public void setContributedBonusFund(String value) {
        contributedBonusFund.setValue(value);
    }

    public void setContributedTotalFunds(String value) {
        contributedTotalFunds.setValue(value);
    }

    public void setToPay(String value) {
        toPay.setValue(value);
    }

    public void setOddsType(String value) {
        oddsType.setValue(value);
    }

    public void setOdds(String value) {
        odds.setValue(value);
    }

    public void setDiscountCardOwner(String value) {
        discountCardOwner.setValue(value);
    }

    public void setDiscountCardNumber(String value) {
        discountCardNumber.setValue(value);
    }

    public void setDiscountCardType(String value) {
        discountCardType.setValue(value);
    }

    public void setDiscountCardBalance(String value) {
        discountCardBalance.setValue(value);
    }

    public void clearDraftArea() {

    }
}