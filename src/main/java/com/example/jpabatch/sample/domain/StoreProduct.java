package com.example.jpabatch.sample.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class StoreProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String storeName;
    private Long productTotalPrice;

    @Builder
    public StoreProduct(String storeName, long productTotalPrice) {
        this.storeName = storeName;
        this.productTotalPrice = productTotalPrice;
    }
}
