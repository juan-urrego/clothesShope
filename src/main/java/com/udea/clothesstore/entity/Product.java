package com.udea.clothesstore.entity;


import lombok.*;


import javax.persistence.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull(message = "Product name is mandatory")
    @Column(name = "prod_name", nullable = false)
    private String productName;

    @NotNull(message = "Description is mandatory")
    @Column(name = "prod_description", nullable = false)
    private String description;

    @Column(name = "pro_prize", nullable = false)
    private double prize;


    @Column(name = "prod_percentage_discount", nullable = false)
    private double percentageDiscount;

    @Transient
    private double prizeDiscount;

    @NotNull(message = "Frontal Image is mandatory")
    @Column(name = "prod_frontal_image_url", nullable = false)
    private String frontalImageUrl;

    @NotNull(message = "Back Image is mandatory")
    @Column(name = "prod_back_image_url", nullable = false)
    private String backImageUrl;

    @Column(name = "prod_visits")
    private int visits;

    @NotNull(message = "Country is mandatory")
    @ManyToOne(optional = false)
    @JoinColumn(name = "country_id", nullable = false)
    private Country country;

    public Product(String productName, String description, double prize, double percentageDiscount, String frontalImageUrl, String backImageUrl, Country country) {
        this.productName = productName;
        this.description = description;
        this.prize = prize;
        this.percentageDiscount = percentageDiscount;
        this.frontalImageUrl = frontalImageUrl;
        this.backImageUrl = backImageUrl;
        this.country = country;
    }

}
