package com.cossinest.homes.payload.request.business;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class DistrictRequest {

    @NotNull(message = "District name can not be null")
    @Size(min = 2, max = 30, message = "District name should have min 2 chars and max 30 chars")
    @Pattern(regexp = "\\A(?!\\s*\\Z).+", message = "District name must consist of the characters .")
    private String name;

    @NotNull(message = "City id can not be null")
    private int city_id;
}
