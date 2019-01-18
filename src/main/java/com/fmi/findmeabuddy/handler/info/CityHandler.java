package com.fmi.findmeabuddy.handler.info;

import com.fmi.findmeabuddy.domain.City;
import com.fmi.findmeabuddy.domain.Country;
import com.fmi.findmeabuddy.repository.CityRepository;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CityHandler {

    private final CityRepository cityRepository;

    public CityHandler(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @GetMapping(value = "/info/country/{id}/city",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<City> getCities(@PathVariable("id") String countryId) {
        Country country = Country.builder()
                .countryId(Integer.parseInt(countryId))
                .build();

        return cityRepository.findAllByCountry(country);
    }
}
