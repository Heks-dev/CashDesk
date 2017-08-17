package ua.org.goservice.cashdesk.model.exception;

import ua.org.goservice.cashdesk.model.util.loader.PropertyLoader;

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

    public static final String INSUFFICIENT_FUNDS = loader.getValue("insufficient_funds");

    public static final String PAYMENT_AMOUNT_EXCEEDED = loader.getValue("payment.amount.exceeded");

    public static final String BOTH_PAYMENT_AMOUNT_EXCEEDED = loader.getValue("both.payment.amount.exceeded");

    public static final String AMOUNT_NOT_MATCH = loader.getValue("amount.not.match");

    public static final String INCORRECT_PHONE_NUM = loader.getValue("incorrect.phone.num");

    public static final String CARD_ALREADY_EXISTS = loader.getValue("card.already.exists");

    public static final String INSUFFICIENT_BONUS_AMOUNT = loader.getValue("insufficient.bonus.amount");

    public static final String CASH_MEMO_NOT_FOUND = loader.getValue("cash.memo.not.found");
}
