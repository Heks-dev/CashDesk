package ua.org.goservice.cashdesk.model.util.json;

import com.google.gson.reflect.TypeToken;
import ua.org.goservice.cashdesk.model.organization.Organization;
import ua.org.goservice.cashdesk.model.warehouse.Product;

import java.lang.reflect.Type;
import java.util.ArrayList;

public enum Token {
    ORGANIZATION_LIST(new TypeToken<ArrayList<Organization>>(){}.getType()),
    PRODUCT_LIST(new TypeToken<ArrayList<Product>>(){}.getType());

    private final Type type;

    Token(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }
}