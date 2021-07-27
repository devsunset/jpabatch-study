package com.example.jpabatch.sample.service;


import com.example.jpabatch.sample.entity.Member;
import com.example.jpabatch.sample.repository.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
public class MemberServiceTest {

    @Autowired
    com.example.jpabatch.sample.service.MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Test
    public void 회원가입() throws Exception {
        //Given
        Member member = new Member();
        member.setName("kim");

        //When
        Long saveId = memberService.join(member);

        //Then
        assertEquals(member, memberRepository.findById(saveId));
    }

    @Test
    public void 중복_회원_예외() throws Exception {
        Assertions.assertThrows(IllegalStateException.class, () -> {
            //Given
            Member member1 = new Member();
            member1.setName("kim");

            Member member2 = new Member();
            member2.setName("kim");

            //When
            memberService.join(member1);
            memberService.join(member2); //예외가 발생해야 한다.

            //Then
            Assertions.fail("예외가 발생해야 한다.");
        });
    }
}