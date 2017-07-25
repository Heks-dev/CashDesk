package ua.org.goservice.cashdesk.model.communication.request;

import ua.org.goservice.cashdesk.model.api.ReadableFilter;

public class Filter {

    private final ReadableFilter apiFilter;
    private final Object val;

    public Filter(ReadableFilter apiFilter) {
        this(apiFilter, null);
    }

    public Filter(ReadableFilter apiFilter, Object val) {
        this.apiFilter = apiFilter;
        this.val = val;
    }

    public ReadableFilter getApiFilter() {
        return apiFilter;
    }

    public Object getVal() {
        return val;
    }
}
