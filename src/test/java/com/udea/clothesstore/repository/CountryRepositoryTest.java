package com.udea.clothesstore.repository;

import com.udea.clothesstore.entity.Country;
import com.udea.clothesstore.enums.CountryCode;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@DataJpaTest
class CountryRepositoryTest {

    @Autowired
    CountryRepository underTest;



    @Test
    void itShouldfindProductByCode() {
        CountryCode code = CountryCode.COL;
        Country country = new Country(code);
        underTest.save(country);
        Country countryExpected = underTest.findByCode(code).get();
        assertThat(countryExpected).isEqualTo(country);
    }
}