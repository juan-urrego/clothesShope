package com.udea.clothesstore.service.impl;

import com.udea.clothesstore.entity.Country;
import com.udea.clothesstore.enums.CountryCode;
import com.udea.clothesstore.exception.ApiRequestException;
import com.udea.clothesstore.repository.CountryRepository;
import com.udea.clothesstore.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CountryServiceImpl implements CountryService {

    final
    CountryRepository countryRepository;

    @Autowired
    public CountryServiceImpl(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @Override
    public Country getCountryByCountryCode(CountryCode code) {
        return countryRepository.findByCode(code).orElseThrow(
                () -> new ApiRequestException("country not found")
        );
    }

    @Override
    public Country saveCountry(Country country) {
        return countryRepository.save(country);
    }
}
