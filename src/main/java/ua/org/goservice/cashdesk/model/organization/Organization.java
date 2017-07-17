package ua.org.goservice.cashdesk.model.organization;

public class Organization {

    private final Integer id;
    private final String name;
    private final Integer priceid;
    private final String type;

    public Organization(Integer id, String name, Integer priceid, String type) {
        this.id = id;
        this.name = name;
        this.priceid = priceid;
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getPriceid() {
        return priceid;
    }

    public String getType() {
        return type;
    }
}
