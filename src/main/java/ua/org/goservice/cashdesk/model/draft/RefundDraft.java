package ua.org.goservice.cashdesk.model.draft;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ua.org.goservice.cashdesk.controller.cashdesk.refund.RefundUIAssistant;
import ua.org.goservice.cashdesk.model.discount.DiscountCard;
import ua.org.goservice.cashdesk.model.discount.DiscountCardSearcher;
import ua.org.goservice.cashdesk.model.trade.transaction.SaleContent;
import ua.org.goservice.cashdesk.model.trade.transaction.SaleUnit;
import ua.org.goservice.cashdesk.model.warehouse.IDGoodSearcher;
import ua.org.goservice.cashdesk.model.warehouse.Product;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class RefundDraft {

    private RefundUIAssistant uiAssistant;
    private ObservableList<RefundDraftEntry> saleList;
    private final SaleContent saleContent;

    private BigDecimal checkSum;
    private DiscountCard discountCard;
    private BigDecimal checkSumToPay;
    private BigDecimal refundSum;
    private Funds funds = new Funds();

    public RefundDraft(RefundUIAssistant uiAssistant,
                       SaleContent saleContent,
                       IDGoodSearcher goodSearcher,
                       DiscountCardSearcher cardSearcher) {
        this.uiAssistant = uiAssistant;
        this.saleContent = saleContent;
        convertToDraftEntry(goodSearcher);
        findDiscountCard(cardSearcher);
        checkSum = calculateSum(saleList);
        checkSumToPay = calculateDiscountSum(checkSum);
        updateCheckDetails();
    }

    private void updateCheckDetails() {
        uiAssistant.setCheckNumber(saleContent.getId().toString());
        uiAssistant.setDataOf(saleContent.getTime());
        uiAssistant.setCheckSum(checkSum.toString());
        if (discountCard != null) {
            uiAssistant.setCardNumber(discountCard.getBonuscardnum().toString());
            uiAssistant.setCardType(DiscountCard.getLocaleType(discountCard.getType()));
        }
        uiAssistant.setCheckSumToPay(checkSumToPay.toString());
        uiAssistant.setCashFund(saleContent.getCash_payment() == null ? null :
                saleContent.getCash_payment().toString());
        uiAssistant.setTerminalFund(saleContent.getTerminal_payment() == null ? null :
                saleContent.getTerminal_payment().toString());
        uiAssistant.setBonusFund(saleContent.getBonus_payment() == null ? null :
                saleContent.getBonus_payment().toString());
    }

    private void findDiscountCard(DiscountCardSearcher cardSearcher) {
        if (saleContent.getBonus_card() != null) {
            discountCard = cardSearcher.searchDiscountByNumber(saleContent.getBonus_card());
        }
    }

    private BigDecimal calculateSum(ObservableList<RefundDraftEntry> list) {
        BigDecimal sum = BigDecimal.ZERO;
        for (RefundDraftEntry draftEntry : list) {
            sum = sum.add(draftEntry.getTotalSum());
        }
        return sum;
    }

    private BigDecimal calculateDiscountSum(BigDecimal sum) {
        BigDecimal result = sum;
        if (discountCard != null) {
            if (discountCard.getType().equals(DiscountCard.DISCOUNT_TYPE)) {
                BigDecimal discountRate = BigDecimal.valueOf(discountCard.getCoefficient()).movePointLeft(2);
                result = sum.multiply(discountRate);
                result = sum.subtract(result).setScale(2, RoundingMode.HALF_UP);
            }
        }
        return result;
    }

    private void calculateRefundSum() {
        BigDecimal sum = BigDecimal.ZERO;
        for (RefundDraftEntry draftEntry : saleList) {
            if (draftEntry.getTotalRefundSum() != null) {
                sum = sum.add(draftEntry.getTotalRefundSum());
            }
        }
        if (sum.compareTo(BigDecimal.ZERO) <= 0) {
            refundSum = null;
            resetFunds();
        } else {
            this.refundSum = calculateDiscountSum(sum);
        }
        uiAssistant.setTotalRefundSum(refundSum == null ? null : refundSum.toString());
    }

    private void convertToDraftEntry(IDGoodSearcher goodSearcher) {
        saleList = FXCollections.observableArrayList();
        List<SaleUnit> saleUnits = saleContent.getArraygoods();
        List<RefundDraftEntry> draftEntries = new ArrayList<>();
        for (SaleUnit saleUnit : saleUnits) {
            Product product = goodSearcher.findProduct(saleUnit.getIdgood());
            RefundDraftEntry draftEntry = new RefundDraftEntry(product, saleUnit.getCountgood());
            draftEntry.setPrice(saleUnit.getPrice());
            draftEntries.add(draftEntry);
        }
        saleList.setAll(draftEntries);
    }

    public void fullRefund(boolean condition) {
        for (RefundDraftEntry draftEntry : saleList) {
            if (condition) {
                draftEntry.setRefundCount(draftEntry.getCount());
            } else {
                draftEntry.setRefundCount(null);
            }
        }
        calculateRefundSum();
    }

    private void resetFunds() {
        refundInCash(BigDecimal.ZERO);
        refundInBonuses(BigDecimal.ZERO);
    }

    public ObservableList<RefundDraftEntry> getSaleList() {
        return FXCollections.unmodifiableObservableList(saleList);
    }

    public void refundInCash(BigDecimal fund) {
        fund = nullIfZero(fund);
        funds.payInCash(fund);
        uiAssistant.setRefundCash(fund == null ? null : fund.toString());
    }

    public void refundInBonuses(BigDecimal fund) {
        fund = nullIfZero(fund);
        funds.payInBonuses(fund);
        uiAssistant.setRefundBonuses(fund == null ? null : fund.toString());
    }

    private BigDecimal nullIfZero(BigDecimal fund) {
        if (fund.compareTo(BigDecimal.ZERO) <= 0) {
            fund = null;
        }
        return fund;
    }

    public DiscountCard getDiscountCard() {
        return discountCard;
    }

    public BigDecimal getTotalContributedFunds() {
        return funds.getTotalContributed();
    }

    public BigDecimal getBonusFund() {
        return funds.getBonuses();
    }

    public BigDecimal getAmountToRefund() {
        return refundSum;
    }

    public BigDecimal getCashFund() {
        return funds.getCash();
    }

    public void calculateChanges() {
        calculateRefundSum();
    }

    public SaleContent getSaleContent() {
        return saleContent;
    }
}
