package com.example.jpabatch.sample.repository;

import com.example.jpabatch.sample.entity.UserStudy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserStudyRepository extends JpaRepository<UserStudy, Long> {

}
