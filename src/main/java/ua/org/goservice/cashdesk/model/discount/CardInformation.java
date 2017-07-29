package ua.org.goservice.cashdesk.model.discount;

import java.math.BigDecimal;

class CardInformation {
    private final Long bonuscardnum;
    private final BigDecimal bonus;
    private final Integer coefficient;
    private final String type;

    CardInformation(Long bonuscardnum, BigDecimal bonus, Integer coefficient, String type) {
        this.bonuscardnum = bonuscardnum;
        this.bonus = bonus;
        this.coefficient = coefficient;
        this.type = type;
    }

    public Long getBonuscardnum() {
        return bonuscardnum;
    }

    public BigDecimal getBonus() {
        return bonus;
    }

    public Integer getCoefficient() {
        return coefficient;
    }

    public String getType() {
        return type;
    }
}
