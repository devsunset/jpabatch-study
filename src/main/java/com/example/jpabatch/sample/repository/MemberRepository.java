package com.example.jpabatch.sample.repository;

import com.example.jpabatch.sample.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MemberRepository extends JpaRepository<Member, Long> {

}
