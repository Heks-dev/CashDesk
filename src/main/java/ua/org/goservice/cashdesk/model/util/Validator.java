package ua.org.goservice.cashdesk.model.util;

public interface Validator<T> {

    void validate(T val);
}
