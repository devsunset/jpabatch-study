package com.example.jpabatch.sample.repository;

import com.example.jpabatch.sample.domain.Teacher;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
}
