package com.cossinest.homes.payload.mappers;

import com.cossinest.homes.domain.concretes.business.City;
import com.cossinest.homes.payload.response.business.CityForAdvertsResponse;
import org.springframework.stereotype.Component;

@Component
public class CityMapper {

    //Adverts için method
    //Cities ==> CityForAdvertsResponse

    public CityForAdvertsResponse mapperCityToCityForAdvertsResponse(City city){

        return CityForAdvertsResponse.builder()
                .cityName(city.getName())
                .amount(city.getAdvertList().size())
                .build();

    }

}
