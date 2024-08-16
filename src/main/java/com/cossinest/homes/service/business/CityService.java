package com.cossinest.homes.service.business;

import com.cossinest.homes.domain.concretes.business.City;
import com.cossinest.homes.domain.concretes.business.Country;
import com.cossinest.homes.domain.enums.Cities;
import com.cossinest.homes.domain.enums.RoleType;
import com.cossinest.homes.exception.ResourceNotFoundException;
import com.cossinest.homes.payload.mappers.CityMapper;
import com.cossinest.homes.payload.messages.ErrorMessages;
import com.cossinest.homes.payload.request.business.CityAdvertTotalRequest;
import com.cossinest.homes.payload.request.business.CityRequest;
import com.cossinest.homes.payload.response.business.CityAdvertTotalResponse;
import com.cossinest.homes.payload.response.business.CityForAdvertsResponse;
import com.cossinest.homes.repository.business.CityRepository;
import com.cossinest.homes.service.helper.MethodHelper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CityService {

  @Autowired
   private CityRepository cityRepository;
  @Autowired
  private CountryService countryService;

    @Autowired
    private  CityMapper cityMapper;
    private final MethodHelper methodHelper;

    //Advert için yazıldı
    public List<CityForAdvertsResponse> getAllCityForAdverts(){

        List<City> cityList = cityRepository.findAll();

        return cityList.stream().map(cityMapper::mapperCityToCityForAdvertsResponse).collect(Collectors.toList());
    }


    public List<City> getAllCity() {
        return cityRepository.findAll();
    }

    @Transactional
    public City getCityById(Long id){
        return cityRepository.findById(id).orElseThrow(()->new ResourceNotFoundException(ErrorMessages.CITY_NOT_FOUND));

    }

    public void resetCityTables() {
        cityRepository.deleteAll();
    }


    public int countAllCities() {

      return cityRepository.countAllCities();
    }

    public void setBuiltInForCity() {

        Long cityId = 1L;

        City city = cityRepository.findById(cityId).orElseThrow(() -> new RuntimeException(ErrorMessages.CITY_NOT_FOUND));
        city.setBuilt_in(Boolean.TRUE);
        cityRepository.save(city);
    }

    public List<City> getByCity(Long countryId) {
        return cityRepository.getByCity(countryId);
    }

    public List<CityAdvertTotalResponse> getCitiesAdvertsTotal(CityAdvertTotalRequest totalRequest, HttpServletRequest request) {

        methodHelper.checkRoles(methodHelper.getUserByHttpRequest(request), RoleType.ADMIN,RoleType.MANAGER);
        
        Set<String> cityNames = totalRequest.getCities().stream()
                .map(String::toLowerCase)
                .collect(Collectors.toSet());

        List<City> cities = cityRepository.findByNameInIgnoreCase(cityNames);

        return cities.stream().map(cityMapper::mapperCityAdvertTotalResponse).collect(Collectors.toList());
    }


//
//    public City saveCity(CityRequest cityRequest) {
//
//        City city = new City();
//        city.setName(cityRequest.getName());
//
//        String cityName =cityRequest.getName();
//        city.setCities( fromName(cityName));
//
//        Country country = countryService.getById(cityRequest.getCountry_id());
//        city.setCountry(country);
//
//        return cityRepository.save(city);
//    }

//    public static Cities fromName(String name) {
//        for (Cities city : Cities.values()) {
//            if (city.getName().equalsIgnoreCase(name)) {
//                return city;
//            }
//        }
//        return null;
//    }



}
