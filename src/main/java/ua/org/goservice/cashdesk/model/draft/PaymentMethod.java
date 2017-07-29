package ua.org.goservice.cashdesk.model.draft;

import ua.org.goservice.cashdesk.model.util.PropertyLoader;

public class PaymentMethod {
    private static final PropertyLoader loader = new PropertyLoader("/strings/payment-method.properties");

    public static final String CASH_PAYMENT_LOCALE = loader.getValue("cash");
    public static final String TERMINAL_PAYMENT_LOCALE = loader.getValue("terminal");
    public static final String BONUSES_PAYMENT_LOCALE = loader.getValue("bonuses");
}
