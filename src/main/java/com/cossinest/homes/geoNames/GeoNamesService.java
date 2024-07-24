package com.cossinest.homes.geoNames;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class GeoNamesService {

    private final GeoNamesCountryRepository geoNamesCountryRepository;
    private final GeoNamesCityRepository geoNamesCityRepository;

    @Transactional
    public void loadCountries(String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split("\t");
                if ("TR".equals(fields[8])) {  // Sadece Türkiye için filtreleme
                    GeoNamesCountry country = new GeoNamesCountry();
                    country.setGeonameId(Long.parseLong(fields[0]));
                    country.setCountryName(fields[4]);
                    country.setCountryCode(fields[8]);
                    geoNamesCountryRepository.save(country);
                }
            }
        }
    }

    @Transactional
    public void loadCities(String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split("\t");
                if ("TR".equals(fields[8])) {  // Sadece Türkiye için filtreleme
                    GeoNamesCity city = new GeoNamesCity();
                    city.setGeonameId(Long.parseLong(fields[0]));
                    city.setCityName(fields[1]);
                    city.setCountryCode(fields[8]);
                    geoNamesCityRepository.save(city);
                }
            }
        }
    }


}
