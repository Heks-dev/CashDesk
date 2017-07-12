package ua.org.goservice.cashdesk.model.api.impl;

public enum ApiFilter implements ua.org.goservice.cashdesk.model.api.ApiFilter {
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
