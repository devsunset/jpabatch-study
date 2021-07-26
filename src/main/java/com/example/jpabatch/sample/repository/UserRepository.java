package com.example.jpabatch.sample.repository;

import com.example.jpabatch.sample.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {
//    jpa.query-methods.query-creation
//    https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation
    User findByEmail(String email);

    @Query("select m from User m where m.email = :email")
    User queryFindByEmail(@Param("email") String email);
}
