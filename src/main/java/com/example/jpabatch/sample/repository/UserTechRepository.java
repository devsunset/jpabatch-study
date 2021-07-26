package com.example.jpabatch.sample.repository;

import com.example.jpabatch.sample.entity.UserTech;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTechRepository extends JpaRepository<UserTech, Long> {

}
