package ua.org.goservice.cashdesk.model.api;

public enum ApiFilter implements ReadableFilter {
    PASSWORD("password"),
    ID("id"),
    BUYERS("type.buyer"),
    STORE_ID("storeid"),
    PRICE_ID("priceid"),
    BARCODE("barcode"),
    DISCOUNT_CARD_NUM("bonus.card.num"),
    CARD_BONUS_AMOUNT("bonus.amount"),
    DISCOUNT_RATE("discount.rate"),
    CARD_HOLDER_NAME("card.holder.name"),
    CARD_HOLDER_SURNAME("card.holder.surname"),
    CARD_HOLDER_PHONE("card.holder.phone"),
    DISCOUNT_CARD_TYPE("card.type");

    private final String value;

    ApiFilter(String value) {
        this.value = value;
    }

    @Override
    public String read() {
        return value;
    }
}
