package ua.org.goservice.cashdesk.model.api;

public enum ApiUrl implements ReadableUrl {
    AUTHORIZATION("auth"),
    ORGANIZATION("org"),
    STOREHOUSE("storehouse"),
    FLYPAGE("flypage"),
    BONUSES("bonuses"),
    SALE("sale"),
    REFUND("refund");

    private final String value;

    ApiUrl(String value) {
        this.value = value;
    }

    @Override
    public String read() {
        return value;
    }
}
