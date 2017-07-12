package ua.org.goservice.cashdesk;

import ua.org.goservice.cashdesk.model.api.impl.Api;
import ua.org.goservice.cashdesk.model.api.impl.ApiFilter;
import ua.org.goservice.cashdesk.model.api.impl.Url;
import ua.org.goservice.cashdesk.model.communication.HttpRequestExecutor;
import ua.org.goservice.cashdesk.model.communication.RequestExecutor;
import ua.org.goservice.cashdesk.model.communication.request.Filter;
import ua.org.goservice.cashdesk.model.communication.request.FilterSet;
import ua.org.goservice.cashdesk.model.communication.request.RequestBuilder;

public class Main {
    public static void main(String[] args) {
        RequestExecutor requestExecutor = new HttpRequestExecutor();
        requestExecutor.sendRequest(new RequestBuilder(Url.AUTHORIZATION, Api.AUTH, new FilterSet(
                new Filter(ApiFilter.PASSWORD, 12345))));
        String response = requestExecutor.getResponse();
        System.out.println(response);
    }
}
