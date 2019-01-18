package com.fmi.findmeabuddy.repository;

import com.fmi.findmeabuddy.domain.City;
import com.fmi.findmeabuddy.domain.Country;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CityRepository extends JpaRepository<City, Long> {
    List<City> findAllByCountry(Country country);
}
