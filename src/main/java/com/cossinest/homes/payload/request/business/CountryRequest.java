package com.cossinest.homes.payload.request.business;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CountryRequest {

    @NotNull
    @Size(max = 30)
    private String name;

    // Constructors
    public CountryRequest() {

    }


    public CountryRequest(String name) {
        this.name = name;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

