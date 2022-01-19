package com.udea.clothesstore.controller;

import com.udea.clothesstore.dto.ProductDto;
import com.udea.clothesstore.entity.Product;
import com.udea.clothesstore.enums.CountryCode;
import com.udea.clothesstore.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    final
    ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(value = "/list")
    public ResponseEntity<List<Product>> getAll() {
        List<Product> list = productService.getProducts();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping(value = "/list/sorted")
    public ResponseEntity<List<ProductDto>> getAllSorted() {
        List<ProductDto> list = productService.getProductsSortedByMostViewed();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Product> getById(@PathVariable(value = "id") int id) {
        return new ResponseEntity<>(productService.getProductById(id), HttpStatus.OK);
    }

    @PostMapping(value = "/add")
    public ResponseEntity<Product> saveProduct(@RequestParam(value = "productName") String productName,
                                               @RequestParam(value = "description") String description,
                                               @RequestParam(value = "prize") double prize,
                                               @RequestParam(value = "percentageDiscount") double percentageDiscount,
                                               @RequestParam(value = "frontalImage")MultipartFile frontalImage,
                                               @RequestParam(value = "backImage") MultipartFile backImage,
                                               @RequestParam(value = "countryCode")CountryCode code) {
        Product product = productService.saveProduct(productName, description, prize, percentageDiscount, frontalImage, backImage, code);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PostMapping(value = "/update/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable(value = "id") int id,
                                                 @RequestParam(value = "productName") String productName,
                                                 @RequestParam(value = "description") String description,
                                                 @RequestParam(value = "prize") double prize,
                                                 @RequestParam(value = "percentageDiscount") double percentageDiscount,
                                                 @RequestParam(value = "frontalImage")MultipartFile frontalImage,
                                                 @RequestParam(value = "backImage") MultipartFile backImage,
                                                 @RequestParam(value = "countryCode")CountryCode code) {
        Product product = productService.updateProduct(id, productName, description, prize, percentageDiscount, frontalImage, backImage, code);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

}
