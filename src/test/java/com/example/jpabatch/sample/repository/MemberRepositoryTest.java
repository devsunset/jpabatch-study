package com.example.jpabatch.sample.repository;

import com.example.jpabatch.sample.entity.Member;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Junit5 Reference
// https://hirlawldo.tistory.com/m/39

@SpringBootTest
@ExtendWith(SpringExtension.class)
class MemberRepositoryTest {

    Logger log = (Logger) LoggerFactory.getLogger(MemberRepositoryTest.class);

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @Transactional
    public void member_crud_test() {
        //Create
        String email = "devsunset@gmail.com";
        Member member = new Member();
        member.setEmail(email);
        member.setNickName("devsunset");
        member.setBirthYear(1978);

        Member savedMember = memberRepository.save(member);
        assertEquals(member.getEmail(), savedMember.getEmail());

        log.info("savedMember createdDate = " + savedMember.getCreatedDate());
        log.info("savedMember modifiedDate = " + savedMember.getModifiedDate());

        //Read
        Member findByEmail = memberRepository.findByEmail(email);
        log.info("findByEmail = " + findByEmail.toString());
        log.info("findByEmail createdDate = " + findByEmail.getCreatedDate());
        log.info("findByEmail modifiedDate = " + findByEmail.getModifiedDate());

        //Update
        findByEmail.setGithub("https://github.com/devsunset");
        savedMember = memberRepository.save(findByEmail);
        assertEquals("https://github.com/devsunset", savedMember.getGithub());

        List<Member> findMember = memberRepository.findAll();
        if(!findMember.isEmpty()){
            log.info("findMember = " + findMember.get(0).toString());
            log.info("findMember createdDate = " + findMember.get(0).getCreatedDate());
            log.info("findMember modifiedDate = " + findMember.get(0).getModifiedDate());

            //Delete
            memberRepository.delete(findMember.get(0));

            List<Member> findMemberSub = memberRepository.findAll();
            log.info(" findMemberSub size : "+findMemberSub.size());
        }
    }
}
