package com.udea.clothesstore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    String productName;
    double prize;
    double prizeDiscount;
    double percentageDiscount;
    String frontalImageUrl;
    String backImageUrl;
}
