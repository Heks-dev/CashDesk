package ua.org.goservice.cashdesk.model.api.impl;

import ua.org.goservice.cashdesk.model.api.ApiVal;

public enum Api implements ApiVal {
    AUTH("api.login"),
    LIST("api.list"),
    NEW("api.new");

    private final String value;

    Api(String value) {
        this.value = value;
    }

    @Override
    public String read() {
        return value;
    }
}
