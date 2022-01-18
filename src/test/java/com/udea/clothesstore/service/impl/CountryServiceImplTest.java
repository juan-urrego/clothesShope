package com.udea.clothesstore.service.impl;

import com.udea.clothesstore.entity.Country;
import com.udea.clothesstore.enums.CountryCode;
import com.udea.clothesstore.exception.ApiRequestException;
import com.udea.clothesstore.repository.CountryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CountryServiceImplTest {

    @Mock
    private CountryRepository countryRepository;
    private CountryServiceImpl underTest;

    @BeforeEach
    void setUp() {
        underTest = new CountryServiceImpl(countryRepository);
    }

    @Test
    void getCountryByCountryCode() {
        CountryCode code = CountryCode.COL;
        Country country = new Country(code);
        when(countryRepository.findByCode(country.getCode()))
                .thenReturn(java.util.Optional.of(country));
        assertEquals(country, underTest.getCountryByCountryCode(code));
    }

    @Test
    void willThrowWhenCountryDoesNotExist() {
        CountryCode code = CountryCode.COL;
        assertThatThrownBy(() -> underTest.getCountryByCountryCode(code))
                .isInstanceOf(ApiRequestException.class)
                .hasMessageContaining("country not found");
    }
}