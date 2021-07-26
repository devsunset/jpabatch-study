package com.example.jpabatch.sample.repository;

import com.example.jpabatch.sample.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberRepository extends JpaRepository<Member, Long> {
//    jpa.query-methods.query-creation
//    https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation
    Member findByEmail(String email);

    @Query("select m from Member m where m.email = :email")
    Member queryFindByEmail(@Param("email") String email);
}
