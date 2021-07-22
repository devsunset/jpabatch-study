package com.example.jpabatch.sample.repository;

import com.example.jpabatch.sample.entity.Tech;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TechRepository extends JpaRepository<Tech, Long> {

}
