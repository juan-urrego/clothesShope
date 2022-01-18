package com.udea.clothesstore.repository;

import com.udea.clothesstore.entity.Country;
import com.udea.clothesstore.enums.CountryCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CountryRepository extends JpaRepository<Country, Integer> {
    Optional<Country> findByCode(CountryCode code);
}
