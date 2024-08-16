package com.cossinest.homes.payload.response.business;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class CityAdvertTotalResponse {
    private String city;
    private Integer advertsTotal;
}
