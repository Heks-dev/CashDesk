package ua.org.goservice.cashdesk.model.employee;

final class EmployeeInformation {
    private final Integer id;
    private final Integer accesslevel;
    private final String name;
    private final String lastname;
    private final String tel;
    private final Integer rating;

    EmployeeInformation(Integer id, Integer accesslevel, String name, String lastname, String tel, Integer rating) {
        this.id = id;
        this.accesslevel = accesslevel;
        this.name = name;
        this.lastname = lastname;
        this.tel = tel;
        this.rating = rating;
    }

    Integer getId() {
        return id;
    }

    Integer getAccesslevel() {
        return accesslevel;
    }

    String getName() {
        return name;
    }

    String getLastname() {
        return lastname;
    }

    String getTel() {
        return tel;
    }

    Integer getRating() {
        return rating;
    }
}
