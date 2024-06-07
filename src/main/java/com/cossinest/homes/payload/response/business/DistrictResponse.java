package com.cossinest.homes.payload.response.business;

import com.cossinest.homes.domain.concretes.business.City;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class DistrictResponse {

    private Long id;
    private String name;
    private City city;
}
