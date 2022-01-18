package com.udea.clothesstore.service;

import com.udea.clothesstore.dto.ProductDto;
import com.udea.clothesstore.entity.Product;
import com.udea.clothesstore.enums.CountryCode;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    List<ProductDto> getProductsSortedByMostViewed();

    List<Product> getProducts();

    Product getProductById(int id);

    Product saveProduct(String productName, String description,
                        double prize, double percentageDiscount,
                        MultipartFile frontalImage, MultipartFile backImage,
                        CountryCode countryCode);

    Product updateProduct(int id, String productName, String description,
                          double prize, double percentageDiscount,
                          MultipartFile frontalImage, MultipartFile backImage,
                          CountryCode countryCode);
}
