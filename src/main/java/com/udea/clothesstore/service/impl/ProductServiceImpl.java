package com.udea.clothesstore.service.impl;

import com.udea.clothesstore.dto.ProductDto;
import com.udea.clothesstore.entity.Country;
import com.udea.clothesstore.entity.Product;
import com.udea.clothesstore.enums.CountryCode;
import com.udea.clothesstore.exception.ApiRequestException;
import com.udea.clothesstore.mapper.IProductMapper;
import com.udea.clothesstore.repository.ProductRepository;
import com.udea.clothesstore.service.AWSS3Service;
import com.udea.clothesstore.service.CountryService;
import com.udea.clothesstore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    final
    ProductRepository productRepository;

    @Autowired
    IProductMapper iProductMapper;

    @Autowired
    AWSS3Service awss3Service;

    @Autowired
    CountryService countryService;

    public static final CountryCode COLOMBIA = CountryCode.COL;
    public static final CountryCode CHILE = CountryCode.CHL;
    public static final CountryCode PERU = CountryCode.PER;
    public static final CountryCode MEXICO = CountryCode.MEX;


    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductDto> getProductsSortedByMostViewed() {
        return productRepository.findTop5ByOrderByVisitsDesc().stream().map(
                product -> {
                    product.setPrizeDiscount(calculatePrizeDiscount(product.getPrize(),
                            product.getPercentageDiscount()));
                    return iProductMapper.modelToDto(product);
                }
        ).collect(Collectors.toList());
    }

    @Override
    public List<Product> getProducts() {
        return productRepository.findAll()
                .stream()
                .peek(product -> product.setPrizeDiscount(calculatePrizeDiscount(product.getPrize(), product.getPercentageDiscount())))
                .collect(Collectors.toList());
    }

    @Override
    public Product getProductById(int id) {
        Product product = productRepository.findById(id).orElseThrow(() ->
                new ApiRequestException("product not found"));
        product.setVisits(product.getVisits() + 1);
        productRepository.save(product);
        product.setPrizeDiscount(calculatePrizeDiscount(product.getPrize(), product.getPercentageDiscount()));
        return product;
    }

    @Override
    public Product saveProduct(String productName, String description, double prize, double percentageDiscount, MultipartFile frontalImage, MultipartFile backImage, CountryCode countryCode) {
        if (isPercentageValueInvalid(percentageDiscount, countryCode))
            throw new ApiRequestException("percentage invalid");
        String frontalKey = awss3Service.uploadFile(frontalImage);
        String backKey = awss3Service.uploadFile(backImage);
        Country country = countryService.getCountryByCountryCode(countryCode);
        int visits = 0;
        Product product = new Product(
                productName,
                description,
                prize,
                percentageDiscount,
                awss3Service.getObjectUrl(frontalKey),
                awss3Service.getObjectUrl(backKey),
                country
        );
        product.setPrizeDiscount(calculatePrizeDiscount(prize, percentageDiscount));
        product.setVisits(visits);
        productRepository.save(product);
        return product;
    }

    @Override
    public Product updateProduct(int id, String productName, String description,
                                 double prize, double percentageDiscount, MultipartFile frontalImage,
                                 MultipartFile backImage, CountryCode countryCode) {
        if (isPercentageValueInvalid(percentageDiscount, countryCode))
            throw new ApiRequestException("percentage invalid");
        String frontalKey = awss3Service.uploadFile(frontalImage);
        String backKey = awss3Service.uploadFile(backImage);
        Country country = countryService.getCountryByCountryCode(countryCode);
        int visits = 0;
        Product product = getProductById(id);
        product.setProductName(productName);
        product.setDescription(description);
        product.setPrize(prize);
        product.setPercentageDiscount(percentageDiscount);
        product.setFrontalImageUrl(awss3Service.getObjectUrl(frontalKey));
        product.setBackImageUrl(awss3Service.getObjectUrl(backKey));
        product.setCountry(country);
        product.setPrizeDiscount(calculatePrizeDiscount(prize, percentageDiscount));
        product.setVisits(visits);
        productRepository.save(product);
        return product;
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
