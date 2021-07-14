package com.example.jpabatch.sample.domain;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(indexes = { @Index(name = "idx_product_backup_1", columnList = "store_id") })
public class ProductBackup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long originId;

    private String name;
    private long price;
    private LocalDate createDate;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "store_id")
    private StoreBackup store;

    @Builder
    public ProductBackup(Product product) {
        this.originId = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.createDate = product.getCreateDate();
    }

    public void changeStore(StoreBackup store) {
        this.store = store;
    }
}
