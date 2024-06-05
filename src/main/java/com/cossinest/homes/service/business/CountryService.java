package com.cossinest.homes.service.business;

import com.cossinest.homes.domain.concretes.business.Country;
import com.cossinest.homes.exception.ResourceNotFoundException;
import com.cossinest.homes.payload.messages.ErrorMessages;
import com.cossinest.homes.repository.business.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CountryService {
    private final CountryRepository countryRepository;


    public Country getCountryById(Long id){
        return countryRepository.findById(id).orElseThrow(()->new ResourceNotFoundException(ErrorMessages.COUNTRY_NOT_FOUND));
    }
}
