package com.cossinest.homes.domain.enums;

public enum Countries {
    ENGLAND("England"),
    TÜRKİYE("Türkiye"),
    DEUTSCHLAND("Deutschland");


    private final String name;

    Countries(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
}
