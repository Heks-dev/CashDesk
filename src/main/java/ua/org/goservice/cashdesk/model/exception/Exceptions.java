package ua.org.goservice.cashdesk.model.exception;

import ua.org.goservice.cashdesk.model.util.PropertyLoader;

public final class Exceptions {
    private static final PropertyLoader loader = new PropertyLoader("/system-message/exception.properties");

    public static final String UNDEFINED_JSON_FORMAT = loader.getValue("undefined.json.format");

    public static final String CONNECTION_PROBLEM = loader.getValue("connection.problem");

    public static final String WRONG_ARGUMENTS_NUMBER = loader.getValue("wrong.args.num");

    public static final String EMPLOYEE_ALREADY_LOGGED = loader.getValue("employee.already.logged");

    public static final String WRONG_PASSWORD_FORMAT = loader.getValue("wrong.password.format");
}
