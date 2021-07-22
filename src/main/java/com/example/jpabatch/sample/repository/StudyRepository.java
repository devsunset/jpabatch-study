package com.example.jpabatch.sample.repository;

import com.example.jpabatch.sample.entity.Study;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyRepository extends JpaRepository<Study, Long> {

}
