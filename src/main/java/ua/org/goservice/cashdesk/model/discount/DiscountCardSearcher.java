package ua.org.goservice.cashdesk.model.discount;

public interface DiscountCardSearcher {

    DiscountCard searchDiscountByNumber(Long cardNumber);
}
