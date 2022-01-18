package com.udea.clothesstore.service.impl;

import com.udea.clothesstore.entity.Country;
import com.udea.clothesstore.entity.Product;
import com.udea.clothesstore.enums.CountryCode;
import com.udea.clothesstore.exception.ApiRequestException;
import com.udea.clothesstore.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    public static final CountryCode COLOMBIA = CountryCode.COL;
    public static final CountryCode CHILE = CountryCode.CHL;
    public static final CountryCode PERU = CountryCode.PER;
    public static final CountryCode MEXICO = CountryCode.MEX;

    @Mock
    private ProductRepository productRepository;
    private ProductServiceImpl underTest;

    @BeforeEach
    void setUp() {
        underTest = new ProductServiceImpl(productRepository);
    }


    @Test
    void canGetProducts() {
        underTest.getProducts();
        verify(productRepository).findAll();
    }

    @Test
    void ItShouldGetProductById() {
        CountryCode col = CountryCode.COL;
        Country country = new Country(col);
        Product product = new Product(
                "nombre",
                "description",
                1234,
                0.5,
                "frontalImage.com",
                "backImage.com",
                country
        );
        product.setVisits(0);
        when(productRepository.findById(1))
                .thenReturn(java.util.Optional.of(product));
        assertEquals(product, underTest.getProductById(1));
    }

    @Test
    void willThrowWhenProductDoesNotExist() {
        assertThatThrownBy(() -> underTest.getProductById(1))
                .isInstanceOf(ApiRequestException.class)
                .hasMessageContaining("product not found");
    }


    @Test
    void itShouldCalculatePrizeDiscount() {
        double prize = 50000;
        double discount = 0.5;
        double total = calculatePrizeDiscount(prize, discount);
        double totalExpected = underTest.calculatePrizeDiscount(prize,discount);
        assertThat(totalExpected).isEqualTo(total);
    }

    @Test
    void isPercentageValueInvalid() {
        double discount = 0.8;
        CountryCode code = CountryCode.COL;
        boolean expected = underTest.isPercentageValueInvalid(discount,code);
        assertThat(expected).isTrue();
    }

    @Test
    void isPercentageValueInvalidWithOtherCountry() {
        double discount = 0.8;
        CountryCode code = CountryCode.CHL;
        boolean expected = underTest.isPercentageValueInvalid(discount,code);
        assertThat(expected).isTrue();
    }

    @Test
    void isPercentageValuevalid() {
        double discount = 0.4;
        CountryCode code = CountryCode.COL;
        boolean expected = underTest.isPercentageValueInvalid(discount,code);
        assertThat(expected).isFalse();
    }

    public double calculatePrizeDiscount(double prize, double percentageDiscount) {
        return prize - (prize * percentageDiscount);
    }

    public boolean isPercentageValueInvalid(double percentageDiscount, CountryCode country) {
        if ((country.equals(COLOMBIA) || country.equals(MEXICO)) && percentageDiscount > 0.50)
            return true;
        if ((country.equals(CHILE) || country.equals(PERU)) && percentageDiscount > 0.30)
            return true;
        return false;
    }
}