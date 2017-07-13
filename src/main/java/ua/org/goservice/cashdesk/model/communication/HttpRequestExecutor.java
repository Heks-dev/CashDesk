package ua.org.goservice.cashdesk.model.communication;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import ua.org.goservice.cashdesk.model.communication.request.RequestBuilder;
import ua.org.goservice.cashdesk.model.exception.CommunicationException;

import java.io.*;
import java.net.CookieManager;
import java.net.CookiePolicy;

public class HttpRequestExecutor implements RequestExecutor {

    private static final CookieManager cookieManager = new CookieManager();
    static {cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);}

    private final OkHttpClient client = new OkHttpClient();
    private String response;

    public HttpRequestExecutor() {
        client.setCookieHandler(cookieManager);
    }

    @Override
    public void sendRequest(RequestBuilder requestBuilder) {
        try {
            Request request = new Request.Builder().url(requestBuilder.extract()).build();
            Response response = client.newCall(request).execute();
            this.response = new String(response.body().bytes(), "UTF-8");
            response.body().close();
        } catch (IOException e) {
            throw new CommunicationException("Сервис недоступен.");
        }
    }

    @Override
    public String getResponse() {
        String returnable = response;
        response = null;
        return returnable;
    }
}
