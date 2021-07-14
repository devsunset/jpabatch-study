package com.example.jpabatch.sample.repository;

import com.example.jpabatch.sample.domain.StoreProduct;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreProductRepository extends JpaRepository<StoreProduct, Long> {
}
