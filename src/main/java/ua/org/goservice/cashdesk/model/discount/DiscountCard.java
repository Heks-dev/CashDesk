package ua.org.goservice.cashdesk.model.discount;

import ua.org.goservice.cashdesk.model.util.loader.PropertyLoader;

import java.math.BigDecimal;

public class DiscountCard {
    private static final PropertyLoader loader = new PropertyLoader(
            "/strings/discount-type.properties");
    public static final String DISCOUNT_TYPE = "discount";
    public static final String ACCUMULATIVE_TYPE = "accumulative";

    private final CardHolder cardHolder;
    private final CardInformation cardInformation;

    public DiscountCard(CardHolder cardHolder, CardInformation cardInformation) {
        this.cardHolder = cardHolder;
        this.cardInformation = cardInformation;
    }

    public Integer getId() {
        return cardHolder.getId();
    }

    public String getName() {
        return cardHolder.getName();
    }

    public String getLastname() {
        return cardHolder.getLastname();
    }

    public String getTel() {
        return cardHolder.getTel();
    }

    public Long getBonuscardnum() {
        return cardInformation.getBonuscardnum();
    }

    public BigDecimal getBonus() {
        return cardInformation.getBonus();
    }

    public Integer getCoefficient() {
        return cardInformation.getCoefficient();
    }

    public String getType() {
        return cardInformation.getType();
    }

    public static String getLocaleType(String discountType) {
        return loader.getValue(discountType);
    }
}
