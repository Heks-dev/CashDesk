package ua.org.goservice.cashdesk.model.trade;

import ua.org.goservice.cashdesk.model.api.ApiUrl;
import ua.org.goservice.cashdesk.model.api.ApiVal;
import ua.org.goservice.cashdesk.model.communication.HttpRequestExecutor;
import ua.org.goservice.cashdesk.model.communication.RequestExecutor;
import ua.org.goservice.cashdesk.model.communication.request.RequestBuilder;
import ua.org.goservice.cashdesk.model.trade.transaction.SaleTransaction;
import ua.org.goservice.cashdesk.model.util.validator.fund.SaleOperationValidator;

public class SaleOperator implements Operator<SaleTransaction> {

    private final RequestExecutor requestExecutor = new HttpRequestExecutor();
    private final SaleOperationValidator validator = new SaleOperationValidator();

    @Override
    public void complete(SaleTransaction transaction) {
        transaction.activateTransaction(true);
        requestExecutor.sendRequest(new RequestBuilder(ApiUrl.SALE, ApiVal.NEW, transaction.serialize()));
        String response = requestExecutor.getResponse();
        // todo debug
        System.out.println(response);
        validator.validate(response);
    }
}
