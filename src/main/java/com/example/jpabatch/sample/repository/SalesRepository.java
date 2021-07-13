package com.example.jpabatch.sample.repository;

import com.example.jpabatch.sample.domain.Sales;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SalesRepository extends JpaRepository<Sales, Long> {
}
