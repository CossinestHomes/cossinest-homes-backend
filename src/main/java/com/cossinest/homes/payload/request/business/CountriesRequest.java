package com.cossinest.homes.payload.request.business;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CountriesRequest {

    @NotNull
    @Size(max = 30)
    private String name;

    // Constructors
    public CountriesRequest() {

    }


    public CountriesRequest(String name) {
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

