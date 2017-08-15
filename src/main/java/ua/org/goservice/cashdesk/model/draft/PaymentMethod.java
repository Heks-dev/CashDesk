package ua.org.goservice.cashdesk.model.draft;

import ua.org.goservice.cashdesk.model.util.loader.PropertyLoader;

public enum PaymentMethod {
    CASH("cash"),
    TERMINAL("terminal"),
    BONUSES("bonuses");

    private static final PropertyLoader loader = new PropertyLoader("/strings/payment-method.properties");
    private final String key;

    PaymentMethod(String value) {
        this.key = value;
    }

    public String getValue() {
        return loader.getValue(key);
    }
}
