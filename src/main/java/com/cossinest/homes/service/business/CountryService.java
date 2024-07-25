package com.cossinest.homes.service.business;

import com.cossinest.homes.domain.concretes.business.Country;
import com.cossinest.homes.exception.ResourceNotFoundException;
import com.cossinest.homes.payload.messages.ErrorMessages;
import com.cossinest.homes.payload.request.business.CountryRequest;
import com.cossinest.homes.repository.business.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@RequiredArgsConstructor
public class CountryService {

    private final CountryRepository countryRepository;
    
    public Country getCountryById(Long id){
        return countryRepository.findById(id).orElseThrow(()->new ResourceNotFoundException(ErrorMessages.COUNTRY_NOT_FOUND));}

    public List<Country> getAllCountry() {

        List<Country> countryList = countryRepository.findAll();

        return countryList;

    }

    public void resetCountryTables() {
        countryRepository.deleteAll();
    }

  public Country getById(int id) {
      return  countryRepository.findById((long) id).orElseThrow(()->new ResourceNotFoundException(ErrorMessages.COUNTRY_NOT_FOUND));
    }

    public int countAllCountries() {
       return countryRepository.cuntAllCountries();
    }

    public Country saveCountry(CountryRequest countryRequest) {
        Country country = new Country();

        country.setName(fromName(countryRequest.getName()));

        return countryRepository.save(country);

    }

    private com.cossinest.homes.domain.enums.Country fromName(String name){

        for (com.cossinest.homes.domain.enums.Country country : com.cossinest.homes.domain.enums.Country.values()) {

            if(country.getName().equalsIgnoreCase(name)){
                return country;
            }

        }
        return null;
    }
}
