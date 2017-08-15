package ua.org.goservice.cashdesk.model.organization;

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
import ua.org.goservice.cashdesk.model.exception.Exceptions;
import ua.org.goservice.cashdesk.model.util.loader.PropertyLoader;
import ua.org.goservice.cashdesk.model.util.json.JsonAgent;
import ua.org.goservice.cashdesk.model.util.json.JsonFormat;
import ua.org.goservice.cashdesk.model.util.json.Token;

import java.util.List;

public class OrganizationManager {
    private static final String DEFAULT_BUYER = "/counterparty/default.properties";
    private static final String ID = "id";
    private final RequestExecutor requestExecutor = new HttpRequestExecutor();
    private Organization our;
    private Organization currentBuyer;
    private ObservableList<Organization> buyers = FXCollections.observableArrayList();

    public OrganizationManager(Integer employeeOrg) {
        syncOurOrganization(employeeOrg);
        syncDefaultBuyer();
        syncBuyers();
    }

    private void syncOurOrganization(Integer employeeOrg) {
        requestExecutor.sendRequest(new RequestBuilder(ApiUrl.ORGANIZATION, ApiVal.LIST,
                new FilterSet(
                        new Filter(ApiFilter.ID, employeeOrg))));
        String orgJson = requestExecutor.getResponse();
        our = JsonAgent.deserialize(orgJson, Organization.class, JsonFormat.SINGLE_OBJECT);
        // todo debug
        System.out.println(getClass().getSimpleName() + " <<our organization>> " + our);
    }

    private void syncDefaultBuyer() {
        PropertyLoader loader = new PropertyLoader(DEFAULT_BUYER);
        Integer buyerID = Integer.valueOf(loader.getValue(ID));
        requestExecutor.sendRequest(new RequestBuilder(ApiUrl.ORGANIZATION, ApiVal.LIST,
                new FilterSet(
                        new Filter(ApiFilter.ID, buyerID))));
        String json = requestExecutor.getResponse();
        System.out.println(json);
        currentBuyer = JsonAgent.deserialize(json, Organization.class, JsonFormat.SINGLE_OBJECT);
        // todo debug
        System.out.println(getClass().getSimpleName() + " <<current buyer organization>> " + currentBuyer);
    }

    public void syncBuyers() {
        requestExecutor.sendRequest(new RequestBuilder(ApiUrl.ORGANIZATION, ApiVal.LIST,
                new FilterSet(
                        new Filter(ApiFilter.BUYERS))));
        String buyersJson = requestExecutor.getResponse();
        List<Organization> buyersList = JsonAgent.deserialize(buyersJson, Token.ORGANIZATION_LIST);
        buyers.setAll(buyersList);
        // todo debug
        System.out.println(buyers);
    }

    public void changeCurrentBuyer(Organization org) {
        if (!buyers.contains(org)) {
            throw new IllegalArgumentException(Exceptions.NON_EXISTENT_BUYER);
        }
        currentBuyer = org;
    }

    public Organization getOurOrganization() {
        return our;
    }

    public Organization getCurrentBuyer() {
        return currentBuyer;
    }

    public ObservableList<Organization> getBuyers() {
        return FXCollections.unmodifiableObservableList(buyers);
    }
}
