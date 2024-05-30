package com.cossinest.homes.payload.request.business;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class CitiesRequest {


    @NotNull(message = "City name can not be empty")
    @Size(max = 30, message = "City name should be at most 30 chars")
    private String name;


    @NotNull(message = "Country id can not be empty")
    private Integer country_id;



}
