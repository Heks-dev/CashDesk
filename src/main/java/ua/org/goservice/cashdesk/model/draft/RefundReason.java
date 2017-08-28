package ua.org.goservice.cashdesk.model.draft;

public class RefundReason {

    private final Integer id;
    private final String name;

    public RefundReason(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
