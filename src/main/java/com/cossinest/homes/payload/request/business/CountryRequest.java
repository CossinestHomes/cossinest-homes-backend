package com.cossinest.homes.payload.request.business;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class CountryRequest {

    @NotNull(message = "Country name must not be empty")
    @Size(max = 30, message = "Country name should be at most 30 chars")
    @Pattern(regexp = "\\A(?!\\s*\\Z).+", message = "Title must consist of the characters .")
    private String name;


}

