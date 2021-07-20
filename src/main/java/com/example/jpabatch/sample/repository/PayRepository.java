
package com.example.jpabatch.sample.repository;

import com.example.jpabatch.sample.domain.Pay;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PayRepository extends CrudRepository<Pay, Long> {

}