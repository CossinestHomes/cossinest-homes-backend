package com.cossinest.homes.domain.enums;

public enum StatusType {

    PENDING("0"),
    APPROVED("1"),
    DECLINED("2"),
    CANCELED("3");

    public final String name;

    StatusType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
