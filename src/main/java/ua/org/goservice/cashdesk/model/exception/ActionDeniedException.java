package ua.org.goservice.cashdesk.model.exception;

public class ActionDeniedException extends RuntimeException {
    public ActionDeniedException(String message) {
        super(message);
    }
}
