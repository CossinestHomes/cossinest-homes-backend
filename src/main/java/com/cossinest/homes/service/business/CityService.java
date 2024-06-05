package com.cossinest.homes.service.business;

import com.cossinest.homes.domain.concretes.business.City;
import com.cossinest.homes.payload.mappers.CityMapper;
import com.cossinest.homes.payload.response.business.CityForAdvertsResponse;
import com.cossinest.homes.repository.business.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CityService {

    private final CityRepository cityRepository;
    private final CityMapper cityMapper;

    //Advert için yazıldı
    public List<CityForAdvertsResponse> getAllCityForAdverts(){

        List<City> cityList = cityRepository.findAll();

        return cityList.stream().map(cityMapper::mapperCityToCityForAdvertsResponse).collect(Collectors.toList());
    }
}
