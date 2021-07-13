package com.example.jpabatch.sample.repository;

import com.example.jpabatch.sample.domain.SalesSum;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SalesSumRepository extends JpaRepository<SalesSum, Long> {
}
