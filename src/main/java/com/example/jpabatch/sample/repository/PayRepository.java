

package com.example.jpabatch.sample.repository;

import com.example.jpabatch.sample.domain.Pay;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PayRepository extends JpaRepository<Pay,Long> {

}