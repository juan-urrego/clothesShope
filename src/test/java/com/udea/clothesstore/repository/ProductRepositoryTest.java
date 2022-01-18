package com.udea.clothesstore.repository;

import com.udea.clothesstore.entity.Country;
import com.udea.clothesstore.entity.Product;
import com.udea.clothesstore.enums.CountryCode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository underTest;
    @Autowired
    private CountryRepository countryRepository;

    @Test
    void itShouldReturnFirst5RowsOrderByVisitsDesc() {
        //given
        List<Product> list = new ArrayList<>();
        CountryCode col = CountryCode.COL;
        Country country = new Country(col);
        countryRepository.save(country);
        Product p1 = new Product(
                "nombre",
                "description",
                1234,
                0.5,
                "frontalImage.com",
                "backImage.com",
                country
        );
        p1.setVisits(10);
        p1.setPrizeDiscount(12343);
        Product p2 = new Product(
                "nombre",
                "description",
                1234,
                0.5,
                "frontalImage.com",
                "backImage.com",
                country
        );
        p2.setVisits(9);
        Product p3 = new Product(
                "nombre",
                "description",
                1234,
                0.5,
                "frontalImage.com",
                "backImage.com",
                country
        );
        p3.setVisits(8);
        Product p4 = new Product(
                "nombre",
                "description",
                1234,
                0.5,
                "frontalImage.com",
                "backImage.com",
                country
        );
        p4.setVisits(7);
        Product p5 = new Product(
                "nombre",
                "description",
                1234,
                0.5,
                "frontalImage.com",
                "backImage.com",
                country
        );
        p5.setVisits(6);
        Product p6 = new Product(
                "nombre",
                "description",
                1234,
                0.5,
                "frontalImage.com",
                "backImage.com",
                country
        );
        p6.setVisits(5);
        list.add(p1);
        list.add(p2);
        list.add(p3);
        list.add(p4);
        list.add(p5);
        list.add(p6);
        underTest.saveAllAndFlush(list);
        List<Product> listExpected = underTest.findTop5ByOrderByVisitsDesc();
        list.remove(p6);
        assertThat(listExpected).isEqualTo(list);
    }
}