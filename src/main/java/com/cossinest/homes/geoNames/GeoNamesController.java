package com.cossinest.homes.geoNames;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/geo")
@RequiredArgsConstructor
public class GeoNamesController {

    private final GeoNamesService geoNamesService;

    @GetMapping("/loadCountries")
    public String loadCountries(@RequestParam String filePath) {
        try {
            geoNamesService.loadCountries(filePath);
            return "Countries loaded successfully!";
        } catch (IOException e) {
            return "Error loading countries: " + e.getMessage();
        }
    }

    @GetMapping("/loadCities")
    public String loadCities(@RequestParam String filePath) {
        try {
            geoNamesService.loadCities(filePath);
            return "Cities loaded successfully!";
        } catch (IOException e) {
            return "Error loading cities: " + e.getMessage();
        }
    }

}
