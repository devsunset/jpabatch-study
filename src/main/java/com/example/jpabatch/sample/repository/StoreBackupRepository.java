package com.example.jpabatch.sample.repository;

import com.example.jpabatch.sample.domain.StoreBackup;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreBackupRepository extends JpaRepository<StoreBackup, Long> {
}
