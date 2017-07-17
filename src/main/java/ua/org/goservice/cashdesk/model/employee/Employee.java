package ua.org.goservice.cashdesk.model.employee;

import ua.org.goservice.cashdesk.model.api.ApiVal;
import ua.org.goservice.cashdesk.model.api.ApiFilter;
import ua.org.goservice.cashdesk.model.api.ApiUrl;
import ua.org.goservice.cashdesk.model.communication.HttpRequestExecutor;
import ua.org.goservice.cashdesk.model.communication.RequestExecutor;
import ua.org.goservice.cashdesk.model.communication.request.Filter;
import ua.org.goservice.cashdesk.model.communication.request.FilterSet;
import ua.org.goservice.cashdesk.model.communication.request.RequestBuilder;
import ua.org.goservice.cashdesk.model.util.json.JsonAgent;
import ua.org.goservice.cashdesk.model.util.json.JsonFormat;
import ua.org.goservice.cashdesk.model.util.Validator;

public class Employee {

    private final RequestExecutor requestExecutor = new HttpRequestExecutor();
    private final Validator<String> validator = new EmployeeResponseValidator();

    private EmployeeInformation employeeInformation;
    private WorkplaceInformation workplaceInformation;

    public Employee(String password) {
        login(password);
    }

    private void login(String password) {
        requestExecutor.sendRequest(new RequestBuilder(ApiUrl.AUTHORIZATION, ApiVal.AUTH,
                new FilterSet(new Filter(ApiFilter.PASSWORD, password))));
        String json = requestExecutor.getResponse();
        validator.validate(json);
        employeeInformation = JsonAgent.deserialize(json, EmployeeInformation.class, JsonFormat.SINGLE_OBJECT);
        workplaceInformation = JsonAgent.deserialize(json, WorkplaceInformation.class, JsonFormat.SINGLE_OBJECT);
    }

    public Integer getId() {
        return employeeInformation.getId();
    }

    public Integer getAccesslevel() {
        return employeeInformation.getAccesslevel();
    }

    public String getName() {
        return employeeInformation.getName();
    }

    public String getLastname() {
        return employeeInformation.getLastname();
    }

    public String getTel() {
        return employeeInformation.getTel();
    }

    public Integer getRating() {
        return employeeInformation.getRating();
    }

    public Integer getCityid() {
        return workplaceInformation.getCityid();
    }

    public Integer getStoreid() {
        return workplaceInformation.getStoreid();
    }

    public Integer getKassaid() {
        return workplaceInformation.getKassaid();
    }

    public Integer getOrgid() {
        return workplaceInformation.getOrgid();
    }

    public Integer getTerminalid() {
        return workplaceInformation.getTerminalid();
    }
}