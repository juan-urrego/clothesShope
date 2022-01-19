package com.udea.clothesstore.service;

import com.udea.clothesstore.entity.Country;
import com.udea.clothesstore.enums.CountryCode;

public interface CountryService {
    Country getCountryByCountryCode(CountryCode code);
    Country saveCountry(Country country);
}
