package ua.org.goservice.cashdesk.model.exception;

public class CommunicationException extends RuntimeException {

    public CommunicationException() {
        super();
    }

    public CommunicationException(String message) {
        super(message);
    }
}
