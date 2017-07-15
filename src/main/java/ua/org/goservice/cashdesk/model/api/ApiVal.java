package ua.org.goservice.cashdesk.model.api;

import ua.org.goservice.cashdesk.model.api.ReadableApi;

public enum ApiVal implements ReadableApi {
    AUTH("api.login"),
    LIST("api.list"),
    NEW("api.new");

    private final String value;

    ApiVal(String value) {
        this.value = value;
    }

    @Override
    public String read() {
        return value;
    }
}
