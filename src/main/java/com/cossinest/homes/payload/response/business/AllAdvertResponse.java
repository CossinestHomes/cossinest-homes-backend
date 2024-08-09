package com.cossinest.homes.payload.response.business;

import com.cossinest.homes.domain.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class AllAdvertResponse {

    private Long id;
    private String title;
    private Status status;
    private Double price;
    private CityForAdvertsResponse city;
    private CountryResponse country;
    private DistrictResponse district;
    private String slug;
    private List<String> base64Photos;

}
