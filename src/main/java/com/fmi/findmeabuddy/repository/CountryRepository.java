package com.fmi.findmeabuddy.repository;

import com.fmi.findmeabuddy.domain.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Long> {
}
