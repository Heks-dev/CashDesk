package ua.org.goservice.cashdesk.model.discount;

import ua.org.goservice.cashdesk.model.api.ApiFilter;
import ua.org.goservice.cashdesk.model.api.ApiUrl;
import ua.org.goservice.cashdesk.model.api.ApiVal;
import ua.org.goservice.cashdesk.model.communication.HttpRequestExecutor;
import ua.org.goservice.cashdesk.model.communication.RequestExecutor;
import ua.org.goservice.cashdesk.model.communication.request.Filter;
import ua.org.goservice.cashdesk.model.communication.request.FilterSet;
import ua.org.goservice.cashdesk.model.communication.request.RequestBuilder;
import ua.org.goservice.cashdesk.model.util.SearchValidator;
import ua.org.goservice.cashdesk.model.util.Validator;
import ua.org.goservice.cashdesk.model.util.json.JsonAgent;
import ua.org.goservice.cashdesk.model.util.json.JsonFormat;

public class DiscountCardManager implements DiscountCardSearcher {

    private final RequestExecutor requestExecutor = new HttpRequestExecutor();
    private final Validator<String> validator = new SearchValidator();

    @Override
    public DiscountCard searchDiscountByNumber(Long cardNumber) {
        requestExecutor.sendRequest(new RequestBuilder(ApiUrl.BONUSES, ApiVal.LIST,
                new FilterSet(
                        new Filter(ApiFilter.DISCOUNT_CARD_NUM, cardNumber)
                )));
        String json = requestExecutor.getResponse();
        validator.validate(json);
        // debug
        System.out.println(json);
        CardHolder cardHolder = JsonAgent.deserialize(json, CardHolder.class, JsonFormat.SINGLE_OBJECT);
        CardInformation cardInformation = JsonAgent.deserialize(json, CardInformation.class, JsonFormat.SINGLE_OBJECT);
        return new DiscountCard(cardHolder, cardInformation);
    }

    public void issueCard() {

    }
}
