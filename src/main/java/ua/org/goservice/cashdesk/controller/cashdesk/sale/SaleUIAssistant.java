package ua.org.goservice.cashdesk.controller.cashdesk.sale;

import javafx.beans.property.StringProperty;

import java.math.BigDecimal;

public class SaleUIAssistant {
    private static final String ODDS_TYPE_DEFAULT = "Сдача";
    private static final String ODDS_TYPE_SCARCITY = "Недостача";

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

    public void setOdds(String value) {
        defineOddsType(value);
        odds.setValue(value);
    }

    private void defineOddsType(String value) {
        if (value != null) {
            BigDecimal bigDecimal = new BigDecimal(value);
            boolean negativeResult = bigDecimal.compareTo(BigDecimal.ZERO) < 0;
            if (negativeResult) {
                if (oddsType.getValue().equals(ODDS_TYPE_DEFAULT)) {
                    oddsType.setValue(ODDS_TYPE_SCARCITY);
                }
            } else {
                if (oddsType.getValue().equals(ODDS_TYPE_SCARCITY)) {
                    oddsType.setValue(ODDS_TYPE_DEFAULT);
                }
            }
        } else {
            oddsType.setValue(ODDS_TYPE_DEFAULT);
        }
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

    void clearUI() {
        checkSum.setValue(null);
        discountRate.setValue(null);
        bonusesAccrued.setValue(null);

        contributedCashFund.setValue(null);
        contributedTerminalFund.setValue(null);
        contributedBonusFund.setValue(null);

        discountCardOwner.setValue(null);
        discountCardNumber.setValue(null);
        discountCardType.setValue(null);
        discountCardBalance.setValue(null);

        toPay.setValue(null);
        contributedTotalFunds.setValue(null);
        oddsType.setValue(ODDS_TYPE_DEFAULT);
        odds.setValue(null);
    }
}