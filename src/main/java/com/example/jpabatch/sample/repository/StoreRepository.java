package com.example.jpabatch.sample.repository;

import com.example.jpabatch.sample.domain.Store;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long> {
}
