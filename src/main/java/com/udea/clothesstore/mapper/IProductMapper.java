package com.udea.clothesstore.mapper;

import com.udea.clothesstore.dto.ProductDto;
import com.udea.clothesstore.entity.Product;

public interface IProductMapper {
    ProductDto modelToDto(Product product);
}
