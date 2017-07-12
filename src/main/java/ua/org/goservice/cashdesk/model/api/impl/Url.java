package ua.org.goservice.cashdesk.model.api.impl;

import ua.org.goservice.cashdesk.model.api.ApiUrl;

public enum Url implements ApiUrl {
    AUTHORIZATION("url.auth"),
    ORGANIZATION("url.org"),
    STOREHOUSE("url.storehouse"),
    FLYPAGE("url.flypage"),
    BONUSES("url.bonuses"),
    SALE("url.sale"),
    REFUND("url.refund");

    private final String value;

    Url(String value) {
        this.value = value;
    }

    @Override
    public String read() {
        return value;
    }
}
