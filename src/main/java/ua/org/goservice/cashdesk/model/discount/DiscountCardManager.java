package ua.org.goservice.cashdesk.model.discount;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ua.org.goservice.cashdesk.model.api.ApiFilter;
import ua.org.goservice.cashdesk.model.api.ApiUrl;
import ua.org.goservice.cashdesk.model.api.ApiVal;
import ua.org.goservice.cashdesk.model.communication.HttpRequestExecutor;
import ua.org.goservice.cashdesk.model.communication.RequestExecutor;
import ua.org.goservice.cashdesk.model.communication.request.Filter;
import ua.org.goservice.cashdesk.model.communication.request.FilterSet;
import ua.org.goservice.cashdesk.model.communication.request.RequestBuilder;
import ua.org.goservice.cashdesk.model.util.validator.SearchValidator;
import ua.org.goservice.cashdesk.model.util.json.Token;
import ua.org.goservice.cashdesk.model.util.loader.FileLoader;
import ua.org.goservice.cashdesk.model.util.validator.ResponseValidator;
import ua.org.goservice.cashdesk.model.util.validator.Validator;
import ua.org.goservice.cashdesk.model.util.json.JsonAgent;
import ua.org.goservice.cashdesk.model.util.json.JsonFormat;

import java.util.List;

public class DiscountCardManager implements DiscountCardSearcher {
    private static final String DISCOUNT_RATES_LOCATION = "/discount/rate.json";

    private final RequestExecutor requestExecutor = new HttpRequestExecutor();
    private final Validator<String> validator = new SearchValidator();
    private ObservableList<Integer> rates;

    public DiscountCardManager() {
        loadDiscountRates();
    }

    @Override
    public DiscountCard searchDiscountByNumber(Long cardNumber) {
        requestExecutor.sendRequest(new RequestBuilder(ApiUrl.BONUSES, ApiVal.LIST,
                new FilterSet(
                        new Filter(ApiFilter.DISCOUNT_CARD_NUM, cardNumber)
                )));
        String json = requestExecutor.getResponse();
        validator.validate(json);
        // todo debug
        System.out.println(json);
        CardHolder cardHolder = JsonAgent.deserialize(json, CardHolder.class, JsonFormat.SINGLE_OBJECT);
        CardInformation cardInformation = JsonAgent.deserialize(json, CardInformation.class, JsonFormat.SINGLE_OBJECT);
        return new DiscountCard(cardHolder, cardInformation);
    }

    public void issueCard(Long cardNumber, Integer cardRate, String cardType,
                          String name, String surname, String phone) {
        requestExecutor.sendRequest(new RequestBuilder(ApiUrl.BONUSES, ApiVal.NEW,
                new FilterSet(
                        new Filter(ApiFilter.DISCOUNT_CARD_NUM, cardNumber),
                        new Filter(ApiFilter.DISCOUNT_RATE, cardRate),
                        new Filter(ApiFilter.DISCOUNT_CARD_TYPE, cardType),
                        new Filter(ApiFilter.CARD_HOLDER_NAME, name),
                        new Filter(ApiFilter.CARD_HOLDER_SURNAME, surname),
                        new Filter(ApiFilter.CARD_HOLDER_PHONE, phone),
                        new Filter(ApiFilter.CARD_BONUS_AMOUNT, 0)
                )));
        ResponseValidator validator = new ResponseValidator();
        validator.validate(requestExecutor.getResponse());
    }

    private void loadDiscountRates() {
        List<Integer> rates = JsonAgent.deserialize(FileLoader.readFile(DISCOUNT_RATES_LOCATION),
                Token.DISCOUNT_RATE_LIST);
        this.rates = FXCollections.observableArrayList(rates);
    }

    public ObservableList<Integer> getRates() {
        return FXCollections.unmodifiableObservableList(rates);
    }
}
