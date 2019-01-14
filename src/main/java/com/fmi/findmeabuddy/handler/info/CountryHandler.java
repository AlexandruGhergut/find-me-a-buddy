package com.fmi.findmeabuddy.handler.info;

import com.fmi.findmeabuddy.domain.Country;
import com.fmi.findmeabuddy.repository.CountryRepository;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CountryHandler {
    private final CountryRepository countryRepository;

    public CountryHandler(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @GetMapping(value = "/info/country",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Country> getCountries() {
        return countryRepository.findAll();
    }
}
