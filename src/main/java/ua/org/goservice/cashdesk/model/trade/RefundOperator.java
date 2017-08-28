package ua.org.goservice.cashdesk.model.trade;

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
import ua.org.goservice.cashdesk.model.draft.RefundReason;
import ua.org.goservice.cashdesk.model.exception.Exceptions;
import ua.org.goservice.cashdesk.model.exception.NotFoundException;
import ua.org.goservice.cashdesk.model.trade.transaction.RefundTransaction;
import ua.org.goservice.cashdesk.model.trade.transaction.SaleContent;
import ua.org.goservice.cashdesk.model.util.json.Token;
import ua.org.goservice.cashdesk.model.util.validator.SearchValidator;
import ua.org.goservice.cashdesk.model.util.json.JsonAgent;
import ua.org.goservice.cashdesk.model.util.json.JsonFormat;
import ua.org.goservice.cashdesk.model.util.validator.Validator;

import java.util.List;

public class RefundOperator implements Operator<RefundTransaction> {

    private final RequestExecutor requestExecutor = new HttpRequestExecutor();

    public SaleContent loadSaleContent(Integer checkID) {
        requestExecutor.sendRequest(new RequestBuilder(ApiUrl.SALE, ApiVal.LIST,
                new FilterSet(
                        new Filter(ApiFilter.ID, checkID)
                )));
        try {
            String json = requestExecutor.getResponse();
            System.out.println(json);
            validateJson(json);
            return JsonAgent.deserialize(json, SaleContent.class, JsonFormat.SINGLE_OBJECT);
        } catch (NotFoundException e) {
            throw new NotFoundException(Exceptions.CASH_MEMO_NOT_FOUND);
        }
    }

    private void validateJson(String json) {
        Validator<String> validator = new SearchValidator();
        validator.validate(json);
    }

    @Override
    public void complete(RefundTransaction transaction) {
        transaction.activateTransaction(true);
        requestExecutor.sendRequest(new RequestBuilder(ApiUrl.REFUND, ApiVal.NEW, transaction.serialize()));
        String response = requestExecutor.getResponse();
        System.out.println(response);
        // todo validate
    }

    public ObservableList<RefundReason> loadRefundReasons() {
        requestExecutor.sendRequest(new RequestBuilder(ApiUrl.REFUND_REASON, ApiVal.LIST));
        String json = requestExecutor.getResponse();
        List<RefundReason> refundReasons = JsonAgent.deserialize(json, Token.REFUND_REASON_LIST);
        return FXCollections.observableArrayList(refundReasons);
    }
}
