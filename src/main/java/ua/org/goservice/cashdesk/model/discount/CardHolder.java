package ua.org.goservice.cashdesk.model.discount;

class CardHolder {
    private final Integer id;
    private final String name;
    private final String lastname;
    private final String tel;

    CardHolder(Integer id, String name, String lastname, String tel) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.tel = tel;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLastname() {
        return lastname;
    }

    public String getTel() {
        return tel;
    }
}
