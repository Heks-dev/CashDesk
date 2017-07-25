package ua.org.goservice.cashdesk.model.util.json;

import com.google.gson.Gson;
import ua.org.goservice.cashdesk.model.exception.Exceptions;

public class JsonAgent {

    private static final Gson gson = new Gson();

    public static <T> T deserialize(String json, Class<T> classOf, JsonFormat format) {
        json = formatting(json, format);
        return gson.fromJson(json, classOf);
    }

    public static <T> T deserialize(String json, Token token) {
        return gson.fromJson(json, token.getType());
    }

    private static String formatting(String json, JsonFormat format) {
        switch (format) {
            case UNCHANGED:
                return json;
            case SINGLE_OBJECT:
                return json.substring(1, json.length() - 1);
            default:
                throw new IllegalArgumentException(Exceptions.UNDEFINED_JSON_FORMAT);
        }
    }
}
