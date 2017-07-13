package ua.org.goservice.cashdesk.model.employee;

import ua.org.goservice.cashdesk.model.Loadable;
import ua.org.goservice.cashdesk.model.api.impl.Api;
import ua.org.goservice.cashdesk.model.api.impl.ApiFilter;
import ua.org.goservice.cashdesk.model.api.impl.Url;
import ua.org.goservice.cashdesk.model.communication.HttpRequestExecutor;
import ua.org.goservice.cashdesk.model.communication.RequestExecutor;
import ua.org.goservice.cashdesk.model.communication.request.Filter;
import ua.org.goservice.cashdesk.model.communication.request.FilterSet;
import ua.org.goservice.cashdesk.model.communication.request.RequestBuilder;
import ua.org.goservice.cashdesk.model.util.json.JsonAgent;
import ua.org.goservice.cashdesk.model.util.json.JsonFormat;

public class Employee implements Loadable {

    private final RequestExecutor requestExecutor = new HttpRequestExecutor();
    private EmployeeInformation employeeInformation;
    private WorkplaceInformation workplaceInformation;

    @Override
    public void loadData(String[] params) {
        verifyParams(params);
        String password = params[0];
        requestExecutor.sendRequest(new RequestBuilder(Url.AUTHORIZATION, Api.AUTH,
                new FilterSet(
                        new Filter(ApiFilter.PASSWORD, password))));
        String json = requestExecutor.getResponse();
        employeeInformation = JsonAgent.deserialize(json, EmployeeInformation.class, JsonFormat.SINGLE_OBJECT);
        workplaceInformation = JsonAgent.deserialize(json, WorkplaceInformation.class, JsonFormat.SINGLE_OBJECT);
    }

    private void verifyParams(String[] params) {
        if (params.length != 1) {
            throw new IllegalArgumentException("Неверное количество аргументов.");
        }
    }

    public Integer getId() {
        return employeeInformation != null ? employeeInformation.getId() : null;
    }

    public Integer getAccesslevel() {
        return employeeInformation != null ? employeeInformation.getAccesslevel() : null;
    }

    public String getName() {
        return employeeInformation != null ? employeeInformation.getName() : null;
    }

    public String getLastname() {
        return employeeInformation != null ? employeeInformation.getLastname() : null;
    }

    public String getTel() {
        return employeeInformation != null ? employeeInformation.getTel() : null;
    }

    public Integer getCityid() {
        return workplaceInformation != null ? workplaceInformation.getCityid() : null;
    }

    public Integer getStoreid() {
        return workplaceInformation != null ? workplaceInformation.getStoreid() : null;
    }

    public Integer getKassaid() {
        return workplaceInformation != null ? workplaceInformation.getKassaid() : null;
    }

    public Integer getOrgid() {
        return workplaceInformation != null ? workplaceInformation.getOrgid() : null;
    }

    public Integer getTerminalid() {
        return workplaceInformation != null ? workplaceInformation.getTerminalid() : null;
    }

    public Integer getRating() {
        return employeeInformation != null ? employeeInformation.getRating() : null;
    }
}