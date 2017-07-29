package ua.org.goservice.cashdesk.model.communication.request;

import ua.org.goservice.cashdesk.model.api.ReadableApi;
import ua.org.goservice.cashdesk.model.api.ReadableUrl;
import ua.org.goservice.cashdesk.model.communication.Extractive;

import static ua.org.goservice.cashdesk.model.communication.request.RequestBuilderProperty.*;

public class RequestBuilder implements Extractive {

    private String formattedRequest;

    public RequestBuilder(ReadableUrl url, ReadableApi api) {
        compose(url, api, null);
    }

    public RequestBuilder(ReadableUrl url, ReadableApi api, String json) {
        compose(url, api, json);
    }

    public RequestBuilder(ReadableUrl url, ReadableApi api, FilterSet filterSet) {
        compose(url, api, filterSet == null ? null : filterSet.extract());
    }


    private void compose(ReadableUrl url, ReadableApi api, String formatted) {
        checkUndefined(url, api);
        StringBuilder builder = new StringBuilder();
        builder.append(getUrl(url.read()))
                .append(getApi(api.read()));
        if (formatted != null) {
            builder.insert(builder.length() - 1, ", ".concat(formatted));
        }
        formattedRequest = builder.toString();
    }

    private void checkUndefined(ReadableUrl url, ReadableApi api) {
        boolean undefined = (url == null || api == null);
        if (undefined) throw new IllegalArgumentException();
    }

    @Override
    public String extract() {
        String result = formattedRequest;
        formattedRequest = null;
        return result;
    }
}
