package com.cossinest.homes.domain.enums;

public enum RoleType {
    ADMIN("Admin"),
    MANAGER("Manager"),
    CUSTOMER("Customer");

    public final String name;

    RoleType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
