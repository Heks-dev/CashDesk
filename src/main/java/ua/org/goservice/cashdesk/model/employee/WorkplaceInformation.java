package ua.org.goservice.cashdesk.model.employee;

final class WorkplaceInformation {

    private final Integer cityid;
    private final Integer storeid;
    private final Integer kassaid;
    private final Integer orgid;
    private final Integer terminalid;

    WorkplaceInformation(Integer cityid, Integer storeid, Integer kassaid, Integer orgid, Integer terminalid) {
        this.cityid = cityid;
        this.storeid = storeid;
        this.kassaid = kassaid;
        this.orgid = orgid;
        this.terminalid = terminalid;
    }

    Integer getCityid() {
        return cityid;
    }

    Integer getStoreid() {
        return storeid;
    }

    Integer getKassaid() {
        return kassaid;
    }

    Integer getOrgid() {
        return orgid;
    }

    Integer getTerminalid() {
        return terminalid;
    }
}
