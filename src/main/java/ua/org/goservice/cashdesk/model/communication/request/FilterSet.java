package ua.org.goservice.cashdesk.model.communication.request;

import ua.org.goservice.cashdesk.model.communication.Extractive;

import static ua.org.goservice.cashdesk.model.communication.request.RequestBuilderProperty.*;

public class FilterSet implements Extractive {

    private String formatted;

    public FilterSet(Filter...filters) {
        compose(filters);
    }

    private void compose(Filter[] filters) {
        StringBuilder builder = new StringBuilder();
        for (Filter filter : filters) {
            if (filter.getVal() != null) {
                builder.append(String.format(getFilter(filter.getApiFilter().read()), filter.getVal()));
            } else {
                builder.append(getFilter(filter.getApiFilter().read()));
            }
            builder.append(", ");
        }
        formatted = builder.substring(0, builder.length() - 2);
    }


    @Override
    public String extract() {
        String result = formatted;
        formatted = null;
        return result;
    }
}
