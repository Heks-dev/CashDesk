package ua.org.goservice.cashdesk.model.util.json;

import com.google.gson.Gson;

public class JsonAgent {

    private static final Gson gson = new Gson();

    public static <T> T deserialize(String json, Class<T> classOf, JsonFormat format) {
        json = formatting(json, format);
        return gson.fromJson(json, classOf);
    }

    private static String formatting(String json, JsonFormat format) {
        switch (format) {
            case UNCHANGED:
                return json;
            case SINGLE_OBJECT:
                return json.substring(1, json.length() - 1);
            default:
                throw new IllegalArgumentException("Undefined json format");
        }
    }
}
