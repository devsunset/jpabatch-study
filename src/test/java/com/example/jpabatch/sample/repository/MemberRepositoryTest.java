package com.example.jpabatch.sample.repository;

import com.example.jpabatch.sample.entity.Member;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Junit5 Reference
// https://hirlawldo.tistory.com/m/39

@SpringBootTest
@ExtendWith(SpringExtension.class)
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void test() {
        String email = "devsunset@gmail.com";
        Member member = new Member();
        member.setEmail(email);
        member.setNickName("devsunset");
        member.setBirthYear(1978);

        Member savedMember = memberRepository.save(member);
        assertEquals(member.getEmail(), savedMember.getEmail());

        member.setGithub("https://github.com/devsunset");
        savedMember = memberRepository.save(member);
        assertEquals("https://github.com/devsunset", savedMember.getGithub());

        List<Member> findMember = memberRepository.findAll();
        if(!findMember.isEmpty()){
            System.out.println("findMember=" + findMember.get(0).toString());
            System.out.println("findMember createdDate = " + findMember.get(0).getCreatedDate());
            System.out.println("findMember modifiedDate = " + findMember.get(0).getModifiedDate());
        }

    }
}
