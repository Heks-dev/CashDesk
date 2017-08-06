package ua.org.goservice.cashdesk.model.trade.transaction;

public class RefundTransaction implements Transaction {

    public RefundTransaction() {

    }

    @Override
    public TransactionContent getTransactionContent() {
        return null;
    }

    @Override
    public String serialize() {
        return null;
    }

    @Override
    public void activateTransaction(boolean yes) {

    }
}
