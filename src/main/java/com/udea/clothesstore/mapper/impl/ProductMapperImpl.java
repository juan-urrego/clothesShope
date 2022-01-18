package com.udea.clothesstore.mapper.impl;

import com.udea.clothesstore.dto.ProductDto;
import com.udea.clothesstore.entity.Product;
import com.udea.clothesstore.mapper.IProductMapper;
import org.springframework.stereotype.Component;

@Component
public class ProductMapperImpl implements IProductMapper {

    @Override
    public ProductDto modelToDto(Product product) {
        return new ProductDto(
                product.getProductName(),
                product.getPrize(),
                product.getPrizeDiscount(),
                product.getPercentageDiscount(),
                product.getFrontalImageUrl(),
                product.getBackImageUrl()
        );
    }
}
