package com.cossinest.homes.service.business;

import com.cossinest.homes.domain.concretes.business.Country;
import com.cossinest.homes.repository.business.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CountryService {

    private final CountryRepository countryRepository;

    public List<Country> getAllCountry() {

        List<Country> countryList = countryRepository.findAll();

        return countryList;
    }
}
