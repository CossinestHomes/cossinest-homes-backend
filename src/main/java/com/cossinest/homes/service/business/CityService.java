package com.cossinest.homes.service.business;

import com.cossinest.homes.domain.concretes.business.City;
import com.cossinest.homes.domain.enums.Cities;
import com.cossinest.homes.domain.enums.Country;
import com.cossinest.homes.exception.ResourceNotFoundException;
import com.cossinest.homes.payload.mappers.CityMapper;
import com.cossinest.homes.payload.messages.ErrorMessages;
import com.cossinest.homes.payload.request.business.CityRequest;
import com.cossinest.homes.payload.response.business.CityForAdvertsResponse;
import com.cossinest.homes.repository.business.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CityService {

  @Autowired
   private CityRepository cityRepository;

    @Autowired
    private  CityMapper cityMapper;

    //Advert için yazıldı
    public List<CityForAdvertsResponse> getAllCityForAdverts(){

        List<City> cityList = cityRepository.findAll();

        return cityList.stream().map(cityMapper::mapperCityToCityForAdvertsResponse).collect(Collectors.toList());
    }


    public List<City> getAllCity() {
        return cityRepository.findAll();
    }

    public City getCityById(Long id){
        return cityRepository.findById(id).orElseThrow(()->new ResourceNotFoundException(ErrorMessages.CITY_NOT_FOUND));

    }

    public void resetCityTables() {
        cityRepository.deleteAll();
    }


    public int countAllCities() {

      return cityRepository.countAllCities();
    }

   /* public City saveCity(CityRequest cityRequest) {

        City city = new City();
        city.setName(cityRequest.getName());

        String cityName =cityRequest.getName();


        city.setCities( fromName(cityName));

        return cityRepository.save();
    }*/

    public static Cities fromName(String name) {
        for (Cities city : Cities.values()) {
            if (city.getName().equalsIgnoreCase(name)) {
                return city;
            }
        }
        return null; // veya Exception fırlatabilirsiniz, isteğe bağlı
    }

    public static Country fromCountry(String name) {
        for (Country country : Country.values()) {
            if (country.getName().equalsIgnoreCase(name)) {
                return country;
            }
        }
        return null; // veya Exception fırlatabilirsiniz, isteğe bağlı
    }


}
