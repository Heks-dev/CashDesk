package ua.org.goservice.cashdesk.model.communication.request;

import ua.org.goservice.cashdesk.model.api.ApiFilter;

public class Filter {

    private final ApiFilter apiFilter;
    private final Object val;

    public Filter(ApiFilter apiFilter, Object val) {
        this.apiFilter = apiFilter;
        this.val = val;
    }

    public ApiFilter getApiFilter() {
        return apiFilter;
    }

    public Object getVal() {
        return val;
    }
}
