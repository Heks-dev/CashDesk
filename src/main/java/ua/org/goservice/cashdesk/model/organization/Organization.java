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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Organization that = (Organization) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (priceid != null ? !priceid.equals(that.priceid) : that.priceid != null) return false;
        return type != null ? type.equals(that.type) : that.type == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (priceid != null ? priceid.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return name + " " + id;
    }
}
