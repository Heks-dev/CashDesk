package ua.org.goservice.cashdesk.model.exception;

import ua.org.goservice.cashdesk.model.util.PropertyLoader;

public final class Exceptions {
    private static final PropertyLoader loader = new PropertyLoader("/strings/exception.properties");

    /** Constants */
    public static final String UNDEFINED_JSON_FORMAT = loader.getValue("undefined.json.format");

    public static final String CONNECTION_PROBLEM = loader.getValue("connection.problem");

    public static final String WRONG_PASSWORD_FORMAT = loader.getValue("wrong.password.format");

    public static final String WRONG_PASSWORD = loader.getValue("wrong.password");

    public static final String UNLOCK_FAILED = loader.getValue("unlock.failed");

    public static final String NON_EXISTENT_BUYER = loader.getValue("non.existent.buyer");

    public static final String PRODUCT_NOT_FOUND = loader.getValue("product.not.found");

    public static final String INVALID_DISCOUNT_CARD_NUMBER_FORMAT = loader
            .getValue("invalid.disc.card.number.format");

    public static final String DISCOUNT_NOT_FOUND = loader.getValue("discount.not.found");

    public static final String INVALID_CURRENCY_FORMAT = loader.getValue("invalid.currency.format");

    public static final String INCORRECT_MEASURE_APIECE_FORMAT = loader.getValue("incorrect.measure.type.format");

    public static final String INCORRECT_MEASURE_FORMAT = loader.getValue("incorrect.measure.format");

    public static final String EXCEED_AVAILABLE_QUANTITY = loader.getValue("exceed_available_quantity");

    public static final String UNABLE_ADD_MORE_FUNDS = loader.getValue("unable.add.more.funds");

    public static final String DISCOUNT_NOT_SPECIFIED = loader.getValue("discount.not.specified");
}
