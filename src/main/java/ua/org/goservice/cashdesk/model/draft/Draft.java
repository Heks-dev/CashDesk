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

    public Draft(SaleUIAssistant uiAssistant) {
        this.uiAssistant = uiAssistant;
        this.draftList = FXCollections.observableArrayList();
    }

    public void addProduct(Product product, BigDecimal count) {
        boolean isAdded = addProductCount(product, count);
        if (!isAdded) draftList.add(new DraftEntry(product, count));
        calculateCheckSum();
        calculateAmountToPaid();
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
        BigDecimal toPaySum = checkSum;
        boolean specifiedDiscount = discountCard != null;
        if (specifiedDiscount) {
            toPaySum = calculateDiscountSums();
        }
        toPay = toPaySum;
        uiAssistant.setToPay(toPay.toString());
    }

    private BigDecimal calculateDiscountSums() {
        BigDecimal toPaySum = checkSum;
        BigDecimal discountAmount;
        BigDecimal bonusAccrued;
        BigDecimal discountCoefficient = BigDecimal.valueOf(discountCard.getCoefficient()).movePointLeft(2);
        boolean discountType = discountCard.getType().equals(DiscountCard.DISCOUNT_TYPE);
        if (discountType) {
            discountAmount = toPaySum.multiply(discountCoefficient)
                    .setScale(2, RoundingMode.HALF_UP);
            System.out.println(discountAmount);
            toPaySum = toPaySum.subtract(discountAmount)
                    .setScale(2, RoundingMode.HALF_UP);
            System.out.println(toPaySum);
        } else {
            bonusAccrued = toPaySum.multiply(discountCoefficient)
                    .setScale(2, RoundingMode.HALF_UP);
            bonusesAccrued = bonusAccrued;
            uiAssistant.setBonusesAccrued(bonusesAccrued.toString());
        }
        return toPaySum;
    }

    public void payInCash(BigDecimal fund) {
        funds.payInCash(fund);
        calculateOdds();
    }

    public void payInTerminal(BigDecimal fund) {
        funds.payInTerminal(fund);
    }

    public void payInBonuses(BigDecimal fund) {
        funds.payInBonuses(fund);
    }

    private void calculateOdds() {

    }

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
}
