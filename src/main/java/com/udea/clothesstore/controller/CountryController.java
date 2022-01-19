package com.udea.clothesstore.controller;

import com.udea.clothesstore.entity.Country;
import com.udea.clothesstore.repository.CountryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/country")
public class CountryController {

    private final CountryRepository countryRepository;


    public CountryController(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @PostMapping(value = "/add")
    public ResponseEntity<Country> saveCountry(@RequestBody Country country) {
        return new ResponseEntity<Country>(countryRepository.save(country), HttpStatus.OK);
    }
}
