package com.cossinest.homes.domain.enums;

public enum Country {
    ENGLAND("England"),
    TÜRKİYE("Türkiye"),
    DEUTSCHLAND("Deutschland");


    private final String name;

    Country(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
}
