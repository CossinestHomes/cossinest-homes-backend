package com.cossinest.homes.domain.enums;

public enum Status {
    PENDING(0, "Pending"),
    ACTIVATED(1, "Activated"),
    REJECTED(2, "Rejected");

    private final int value;
    private final String description;

    Status(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public int getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }


    public static int fromValue(int value) {
        for (Status status : Status.values()) {
            if (status.getValue() == value) {
                return status.getValue();
            }
        }
        throw new IllegalArgumentException("Invalid status value: " + value);
    }
}
