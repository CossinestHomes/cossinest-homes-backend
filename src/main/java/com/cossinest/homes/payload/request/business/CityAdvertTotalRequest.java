package com.cossinest.homes.payload.request.business;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CityAdvertTotalRequest {

    @NotNull
    private Set<String>  cities;




}
