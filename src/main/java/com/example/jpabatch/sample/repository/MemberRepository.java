package com.example.jpabatch.sample.repository;

import com.example.jpabatch.sample.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MemberRepository extends JpaRepository<Member, Long> {

//    jpa.query-methods.query-creation
//    https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation
    Member findByEmail(String email);

}
