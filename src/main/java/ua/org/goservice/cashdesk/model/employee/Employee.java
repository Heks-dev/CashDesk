package ua.org.goservice.cashdesk.model.employee;

import ua.org.goservice.cashdesk.model.util.json.JsonAgent;
import ua.org.goservice.cashdesk.model.util.json.JsonFormat;

public class Employee {

    private EmployeeInformation employeeInformation;
    private WorkplaceInformation workplaceInformation;

    public Employee(String json) {
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