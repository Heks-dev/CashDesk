package ua.org.goservice.cashdesk.model.trade;

import ua.org.goservice.cashdesk.model.trade.transaction.Transaction;

public interface Operator<T extends Transaction> {
    void complete(T transaction);
}
