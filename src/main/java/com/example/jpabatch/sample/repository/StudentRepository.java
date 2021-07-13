package com.example.jpabatch.sample.repository;

import com.example.jpabatch.sample.domain.Student;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
