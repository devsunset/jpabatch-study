package com.example.jpabatch.sample.repository;

import com.example.jpabatch.sample.entity.Member;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Junit5 Reference
// https://hirlawldo.tistory.com/m/39
@SpringBootTest
@ExtendWith(SpringExtension.class)
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void save() {
        // given
        String id = "id1";
        Member member = new Member();
        member.setId(id);
        member.setUsername("test");
        member.setAge(1);

        // when
        final Member savedMember = memberRepository.save(member);

        // then
        assertEquals(member.getUsername(), savedMember.getUsername());
    }
}
