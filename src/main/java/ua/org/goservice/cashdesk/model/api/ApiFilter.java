package ua.org.goservice.cashdesk.model.api;

import ua.org.goservice.cashdesk.model.api.ReadableFilter;

public enum ApiFilter implements ReadableFilter {
    PASSWORD("password"),
    ID("id");

    private final String value;

    ApiFilter(String value) {
        this.value = value;
    }

    @Override
    public String read() {
        return value;
    }
}
