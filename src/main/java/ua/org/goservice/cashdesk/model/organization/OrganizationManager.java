package ua.org.goservice.cashdesk.model.organization;

import ua.org.goservice.cashdesk.model.communication.HttpRequestExecutor;
import ua.org.goservice.cashdesk.model.communication.RequestExecutor;

import java.util.List;

public class OrganizationManager {

    private final RequestExecutor requestExecutor = new HttpRequestExecutor();
    private Organization our;
    private Organization currentBuyer;
    private List<Organization> buyers;

    public OrganizationManager() {

    }
}
