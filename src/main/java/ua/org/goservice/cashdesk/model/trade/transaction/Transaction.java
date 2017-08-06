package ua.org.goservice.cashdesk.model.trade.transaction;

public interface Transaction<T extends TransactionContent> {

    String ACTIVE = "yes";

    T getTransactionContent();
    String serialize();
    void activateTransaction(boolean yes);
}
