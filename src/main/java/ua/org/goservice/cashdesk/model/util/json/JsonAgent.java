package ua.org.goservice.cashdesk.model.util.json;

import com.google.gson.Gson;
import ua.org.goservice.cashdesk.model.exception.Exceptions;
import ua.org.goservice.cashdesk.model.util.loader.FileLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class JsonAgent {

    private static final Gson gson = new Gson();

    public static <T> T deserialize(String json, Class<T> classOf, JsonFormat format) {
        json = formatting(json, format);
        return gson.fromJson(json, classOf);
    }

    public static <T> T deserialize(String json, Token token) {
        return gson.fromJson(json, token.getType());
    }

    public static String serialize(Object src, JsonFormat format) {
        String json = gson.toJson(src);
        return formatting(json, format);
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
