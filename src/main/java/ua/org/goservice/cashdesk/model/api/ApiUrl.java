package ua.org.goservice.cashdesk.model.api;

import ua.org.goservice.cashdesk.model.api.ReadableUrl;

public enum ApiUrl implements ReadableUrl {
    AUTHORIZATION("url.auth"),
    ORGANIZATION("url.org"),
    STOREHOUSE("url.storehouse"),
    FLYPAGE("url.flypage"),
    BONUSES("url.bonuses"),
    SALE("url.sale"),
    REFUND("url.refund");

    private final String value;

    ApiUrl(String value) {
        this.value = value;
    }

    @Override
    public String read() {
        return value;
    }
}
