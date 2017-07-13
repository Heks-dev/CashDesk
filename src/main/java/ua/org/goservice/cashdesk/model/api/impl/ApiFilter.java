package ua.org.goservice.cashdesk.model.api.impl;

import ua.org.goservice.cashdesk.model.api.ReadableFilter;

public enum ApiFilter implements ReadableFilter {
    PASSWORD("password");

    private final String value;

    ApiFilter(String value) {
        this.value = value;
    }

    @Override
    public String read() {
        return value;
    }
}
