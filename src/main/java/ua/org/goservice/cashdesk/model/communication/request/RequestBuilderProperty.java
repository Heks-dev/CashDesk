package ua.org.goservice.cashdesk.model.communication.request;

import ua.org.goservice.cashdesk.model.util.loader.PropertyLoader;

final class RequestBuilderProperty {

    private static final String URL = "url";
    private final static PropertyLoader urlProperty = new PropertyLoader("/request/url.properties");
    private final static PropertyLoader apiProperty = new PropertyLoader("/request/api.properties");
    private final static PropertyLoader filterProperty = new PropertyLoader("/request/filter.properties");

    static String getUrl(String key) {
        return urlProperty.getValue(URL) + urlProperty.getValue(key);
    }

    static String getApi(String key) {
        return apiProperty.getValue(key);
    }

    static String getFilter(String key) {
        return filterProperty.getValue(key);
    }
}
