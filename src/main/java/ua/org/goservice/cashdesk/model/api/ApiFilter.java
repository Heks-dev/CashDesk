package ua.org.goservice.cashdesk.model.api;

public enum ApiFilter implements ReadableFilter {
    PASSWORD("password"),
    ID("id"),
    BUYERS("type.buyer"),
    STORE_ID("storeid"),
    PRICE_ID("priceid"),
    BARCODE("barcode"),
    DISCOUNT_CARD_NUM("bonus.card.num");

    private final String value;

    ApiFilter(String value) {
        this.value = value;
    }

    @Override
    public String read() {
        return value;
    }
}
