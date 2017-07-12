package ua.org.goservice.cashdesk.model.communication;

import ua.org.goservice.cashdesk.model.communication.request.RequestBuilder;

public interface RequestExecutor {

    void sendRequest(RequestBuilder requestBuilder);

    String getResponse();
}
