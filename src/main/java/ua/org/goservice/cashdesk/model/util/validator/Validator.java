package ua.org.goservice.cashdesk.model.util.validator;

public interface Validator<T> {

    void validate(T val);
}
