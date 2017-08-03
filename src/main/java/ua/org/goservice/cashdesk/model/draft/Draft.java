package ua.org.goservice.cashdesk.model.draft;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ua.org.goservice.cashdesk.controller.cashdesk.sale.SaleUIAssistant;
import ua.org.goservice.cashdesk.model.discount.DiscountCard;
import ua.org.goservice.cashdesk.model.warehouse.Product;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Draft {

    private final SaleUIAssistant uiAssistant;
    private ObservableList<DraftEntry> draftList;

    private final Funds funds = new Funds();
    private BigDecimal checkSum;
    private BigDecimal toPay;
    private BigDecimal bonusesAccrued;
    private DiscountCard discountCard;
    private BigDecimal odds;

    public Draft(SaleUIAssistant uiAssistant) {
        this.uiAssistant = uiAssistant;
        this.draftList = FXCollections.observableArrayList();
    }

    public void addProduct(Product product, BigDecimal count) {
        boolean isAdded = addProductCount(product, count);
        if (!isAdded) draftList.add(new DraftEntry(product, count));
        calculateCheckSum();
        calculateAmountToPaid();
        calculateOdds();
    }

    private boolean addProductCount(Product product, BigDecimal count) {
        for (DraftEntry draftEntry : draftList) {
            boolean heldEarlier = draftEntry.getBarcode().equals(product.getBarcode());
            if (heldEarlier) {
                draftEntry.addQuantity(count);
                return true;
            }
        }
        return false;
    }

    private void calculateCheckSum() {
        BigDecimal sum = new BigDecimal(0);
        for (DraftEntry draftEntry : draftList) {
            sum = sum.add(draftEntry.getTotalSum());
        }
        checkSum = sum;
        uiAssistant.setCheckSum(checkSum.toString());
    }

    private void calculateAmountToPaid() {
        BigDecimal toPaySum;
        boolean specifiedDiscount = discountCard != null;
        if (specifiedDiscount) toPaySum = calculateDiscountSums();
        else toPaySum = checkSum;
        toPay = toPaySum;
        uiAssistant.setToPay(toPay.toString());
        calculateOdds();
    }

    /**
     * Calculating Discount
     */
    private BigDecimal calculateDiscountSums() {
        BigDecimal toPaySum = checkSum;
        BigDecimal discountCoefficient = BigDecimal.valueOf(discountCard.getCoefficient()).movePointLeft(2);
        boolean discountType = discountCard.getType().equals(DiscountCard.DISCOUNT_TYPE);
        if (discountType) {
            toPaySum = calculateDiscount(toPaySum, discountCoefficient);
        } else {
            calculateAccruedBonuses(toPaySum, discountCoefficient);
        }
        return toPaySum;
    }

    private BigDecimal calculateDiscount(BigDecimal toPaySum, BigDecimal discountCoefficient) {
        BigDecimal discountAmount = toPaySum.multiply(discountCoefficient)
                .setScale(2, RoundingMode.HALF_UP);
        toPaySum = toPaySum.subtract(discountAmount)
                .setScale(2, RoundingMode.HALF_UP);
        return toPaySum;
    }

    /**
     * Accumulative discount -
     *         charging bonuses
     */
    private void calculateAccruedBonuses(BigDecimal toPaySum, BigDecimal discountCoefficient) {
        if (funds.getBonuses() == null) {
            chargeBonuses(toPaySum, discountCoefficient);
        } else {
            if (bonusesAccrued != null) {
                cancelChargeBonuses();
            }
        }
    }

    private void chargeBonuses(BigDecimal toPaySum, BigDecimal discountCoefficient) {
        bonusesAccrued = toPaySum.multiply(discountCoefficient)
                .setScale(2, RoundingMode.HALF_UP);
    }

    private void cancelChargeBonuses() {
        bonusesAccrued = null;
        uiAssistant.setBonusesAccrued(null);
    }

    /**
     * Contributing funds area   /  Calculate odds
     */
    public void payInCash(BigDecimal fund) {
        fund = nullIfZero(fund);
        funds.payInCash(fund);
        calculateOdds();
        uiAssistant.setContributedCashFund(fund == null ? null : fund.toString());
        uiAssistant.setContributedTotalFunds(funds.getTotalContributed() == null ?
                null : funds.getTotalContributed().toString());
    }

    public void payInTerminal(BigDecimal fund) {
        fund = nullIfZero(fund);
        funds.payInTerminal(fund);
        calculateOdds();
        uiAssistant.setContributedTerminalFund(fund == null ? null : fund.toString());
        uiAssistant.setContributedTotalFunds(funds.getTotalContributed() == null ?
                null : funds.getTotalContributed().toString());
    }

    public void payInBonuses(BigDecimal fund) {
        fund = nullIfZero(fund);
        funds.payInBonuses(fund);
        calculateOdds();
        calculateDiscountSums();
        uiAssistant.setContributedBonusFund(fund == null ? null : fund.toString());
        uiAssistant.setContributedTotalFunds(funds.getTotalContributed() == null ?
                null : funds.getTotalContributed().toString());
    }

    private BigDecimal nullIfZero(BigDecimal fund) {
        if (fund.compareTo(BigDecimal.ZERO) <= 0) {
            fund = null;
        }
        return fund;
    }

    private void calculateOdds() {
        if (funds.getTotalContributed() == null) {
            odds = null;
        } else {
            odds = funds.getTotalContributed().subtract(toPay);
        }
        uiAssistant.setOdds(odds == null ? null : odds.toString());
    }

    /**
     * Discount card area
     */
    public void setDiscountCard(DiscountCard discountCard) {
        this.discountCard = discountCard;
        updateDiscountCardUIArea();
        calculateAmountToPaid();
    }

    private void updateDiscountCardUIArea() {
        uiAssistant.setDiscountCardOwner(discountCard.getName());
        uiAssistant.setDiscountCardNumber(discountCard.getBonuscardnum().toString());
        uiAssistant.setDiscountCardType(discountCard.getLocaleType(discountCard.getType()));
        if (discountCard.getType().equals(DiscountCard.DISCOUNT_TYPE)) {
            uiAssistant.setDiscountCardBalance("-");
            uiAssistant.setDiscountRate(discountCard.getCoefficient().toString());
        } else if (discountCard.getType().equals(DiscountCard.ACCUMULATIVE_TYPE)) {
            uiAssistant.setDiscountCardBalance(discountCard.getBonus().toString());
        }
    }

    public ObservableList<DraftEntry> getDraftList() {
        return FXCollections.unmodifiableObservableList(draftList);
    }

    public BigDecimal getAmountToPay() {
        return toPay;
    }

    public BigDecimal getOdds() {
        return odds;
    }

    public BigDecimal getTotalContributedFunds() {
        return funds.getTotalContributed();
    }

    public BigDecimal getCashFund() {
        return funds.getCash();
    }

    public BigDecimal getTerminalFund() {
        return funds.getTerminal();
    }

    public BigDecimal getBonusFund() {
        return funds.getBonuses();
    }

    public DiscountCard getDiscountCard() {
        return discountCard;
    }
}
