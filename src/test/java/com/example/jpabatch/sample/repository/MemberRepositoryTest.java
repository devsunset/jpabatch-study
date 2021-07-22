package com.example.jpabatch.sample.repository;

import com.example.jpabatch.sample.entity.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
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

    @Autowired
    private TechRepository techRepository;

    @Autowired
    private StudyRepository studyRepository;

    @Autowired
    private MemberTechRepository memberTechRepository;

    @Autowired
    private MemberStudyRepository memberStudyRepository;

    @PersistenceContext
    private EntityManager entityManager;

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
        for (Member memberentity : findMember) {
            System.out.println("member = " + member);

            log.info("findMember = " + memberentity.toString());
            log.info("findMember createdDate = " + memberentity.getCreatedDate());
            log.info("findMember modifiedDate = " + memberentity.getModifiedDate());

            //Delete
            memberRepository.delete(memberentity);

            List<Member> findMemberSub = memberRepository.findAll();
            log.info(" findMemberSub size : "+findMemberSub.size());
        }
    }

    @Test
    @Transactional
    public void member_complex_test() {
        String email = "devsunset@gmail.com";
        Member member = new Member();
        member.setEmail(email);
        member.setNickName("devsunset");
        member.setBirthYear(1978);
        Member savedMember = memberRepository.save(member);
        assertEquals(member.getEmail(), savedMember.getEmail());

        Tech tech = new Tech();
        tech.setCategory("LANGUAGE");
        tech.setItem("JAVA");
        tech.setIcon("icon_location");
        Tech saveTech = techRepository.save(tech);
        assertEquals(tech.getItem(), saveTech.getItem());

        Study study = new Study();
        study.setSubject("programming study");
        study.setTitle("Hello World");
        Study savedStudy = studyRepository.save(study);
        assertEquals(study.getTitle(), savedStudy.getTitle());

        MemberTech memberTech = new MemberTech();
        memberTech.setMember(member);
        memberTech.setTech(tech);
        MemberTech savedMemberTech =  memberTechRepository.save(memberTech);
        assertEquals(memberTech.getMember(), savedMemberTech.getMember());
        assertEquals(memberTech.getTech(), savedMemberTech.getTech());

        MemberStudy memberStudy = new MemberStudy();
        memberStudy.setMember(member);
        memberStudy.setStudy(study);
        MemberStudy savedMemberStudy = memberStudyRepository.save(memberStudy);
        assertEquals(memberStudy.getMember(), savedMemberStudy.getMember());
        assertEquals(memberStudy.getStudy(), savedMemberStudy.getStudy());

        List<Member> findMember = memberRepository.findAll();
        for (Member memberentity : findMember) {
            log.info("findMember = " + memberentity.toString());
            log.info("findMember createdDate = " + memberentity.getCreatedDate());
            log.info("findMember modifiedDate = " + memberentity.getModifiedDate());
        }

        String jpql = "select m from Member as m where m.email = 'devsunset@gmail.com'";
        List<Member> resultList = entityManager.createQuery(jpql, Member.class).getResultList();
        for (Member memberentity : resultList) {
            log.info("findMember = " + memberentity.toString());
            log.info("findMember createdDate = " + memberentity.getCreatedDate());
            log.info("findMember modifiedDate = " + memberentity.getModifiedDate());
        }

        TypedQuery<Member> query = entityManager.createQuery("SELECT m FROM Member m", Member.class);
        List<Member> resultList1 = query.getResultList();
        for (Member memberentity : resultList1) {
            log.info("findMember = " + memberentity.toString());
        }

        Query querySub = entityManager.createQuery("SELECT m.email, m.birthYear FROM Member m");

        List resultList2 = querySub.getResultList();
        for (Object o : resultList2) {
            Object[] result = (Object[]) o;
            log.info("email = " + result[0]);
            log.info("birthYear = " + result[1]);
        }

    }
}
