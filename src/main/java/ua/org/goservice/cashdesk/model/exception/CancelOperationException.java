package ua.org.goservice.cashdesk.model.exception;

public class CancelOperationException extends RuntimeException {
    public CancelOperationException() {
        super();
    }

    public CancelOperationException(String message) {
        super(message);
    }
}
