package com.udea.clothesstore.repository;

import com.udea.clothesstore.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findTop5ByOrderByVisitsDesc();
}
