package ua.org.goservice.cashdesk.controller.control;

import ua.org.goservice.cashdesk.model.util.PropertyLoader;

public class AlertHeader {
    private static final PropertyLoader loader = new PropertyLoader("/strings/alert-header.properties");

    public static final String PRODUCT_COUNT_VALIDATION = loader.getValue("prod.count.validation");
    public static final String ADD_PRODUCT_ERROR = loader.getValue("add.product.error");
}
