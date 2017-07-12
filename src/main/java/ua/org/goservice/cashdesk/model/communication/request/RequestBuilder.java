package ua.org.goservice.cashdesk.model.communication.request;

import ua.org.goservice.cashdesk.model.api.ApiVal;
import ua.org.goservice.cashdesk.model.api.ApiUrl;
import ua.org.goservice.cashdesk.model.communication.Extractive;

import static ua.org.goservice.cashdesk.model.communication.request.RequestBuilderProperty.*;

public class RequestBuilder implements Extractive {

    private String formattedRequest;

    public RequestBuilder(ApiUrl url, ApiVal api) {
        compose(url, api, null);
    }

    public RequestBuilder(ApiUrl url, ApiVal api, String json) {
        compose(url, api, json);
    }

    public RequestBuilder(ApiUrl url, ApiVal api, FilterSet filterSet) {
        compose(url, api, filterSet == null ? null : filterSet.extract());
    }


    private void compose(ApiUrl url, ApiVal api, String formatted) {
        checkUndefined(url, api);
        StringBuilder builder = new StringBuilder();
        builder.append(getUrl(url.read()))
                .append(getApi(api.read()));
        if (formatted != null) {
            builder.insert(builder.length() - 1, ", ".concat(formatted));
        }
        formattedRequest = builder.toString();
    }

    private void checkUndefined(ApiUrl url, ApiVal api) {
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
