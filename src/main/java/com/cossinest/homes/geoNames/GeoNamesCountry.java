package com.cossinest.homes.geoNames;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class GeoNamesCountry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long geonameId;
    private String countryName;
    private String countryCode;


}
