package com.example.jpabatch.sample.repository;

import com.example.jpabatch.sample.entity.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.assertj.core.api.Assertions;
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
@Transactional
class UserRepositoryTest {

    Logger log = (Logger) LoggerFactory.getLogger(UserRepositoryTest.class);

    @Autowired
    private UserRepository UserRepository;

    @Autowired
    private TechRepository techRepository;

    @Autowired
    private StudyRepository studyRepository;

    @Autowired
    private UserTechRepository UserTechRepository;

    @Autowired
    private UserStudyRepository UserStudyRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void User_crud_test() {
        //Create
        String email = "devsunset@gmail.com";
        User User = new User();
        User.setEmail(email);
        User.setNickName("devsunset");
        User.setBirthYear(1978);

        User savedUser = UserRepository.save(User);
        assertEquals(User.getEmail(), savedUser.getEmail());

        log.info("savedUser createdDate = " + savedUser.getCreatedDate());
        log.info("savedUser modifiedDate = " + savedUser.getModifiedDate());

        //Read
        User findByEmail = UserRepository.findByEmail(email);
        log.info("findByEmail = " + findByEmail.toString());
        log.info("findByEmail createdDate = " + findByEmail.getCreatedDate());
        log.info("findByEmail modifiedDate = " + findByEmail.getModifiedDate());

        //Update
        findByEmail.setGithub("https://github.com/devsunset");
        savedUser = UserRepository.save(findByEmail);
        assertEquals("https://github.com/devsunset", savedUser.getGithub());

        List<User> findUser = UserRepository.findAll();
        for (User Userentity : findUser) {
            System.out.println("User = " + User);

            log.info("findUser = " + Userentity.toString());
            log.info("findUser createdDate = " + Userentity.getCreatedDate());
            log.info("findUser modifiedDate = " + Userentity.getModifiedDate());

            //Delete
            UserRepository.delete(Userentity);

            List<User> findUserSub = UserRepository.findAll();
            log.info(" findUserSub size : "+findUserSub.size());
        }
    }

    @Test
    public void User_complex_test() {
        String email = "devsunset@gmail.com";
        User user = new User();
        user.setEmail(email);
        user.setNickName("devsunset");
        user.setBirthYear(1978);
        User savedUser = UserRepository.save(user);
        assertEquals(user.getEmail(), savedUser.getEmail());

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

        UserTech userTech = new UserTech();
        userTech.setUser(user);
        userTech.setTech(tech);
        UserTech savedUserTech =  UserTechRepository.save(userTech);
        assertEquals(userTech.getUser(), savedUserTech.getUser());
        assertEquals(userTech.getTech(), savedUserTech.getTech());

        UserStudy userStudy = new UserStudy();
        userStudy.setUser(user);
        userStudy.setStudy(study);
        UserStudy savedUserStudy = UserStudyRepository.save(userStudy);
        assertEquals(userStudy.getUser(), savedUserStudy.getUser());
        assertEquals(userStudy.getStudy(), savedUserStudy.getStudy());

        List<User> findUser = UserRepository.findAll();
        for (User userentity : findUser) {
            log.info("findUser = " + userentity.toString());
            log.info("findUser createdDate = " + userentity.getCreatedDate());
            log.info("findUser modifiedDate = " + userentity.getModifiedDate());
        }

        // JPQL
        String jpql = "select m from User as m where m.email = 'devsunset@gmail.com'";
        List<User> resultList = entityManager.createQuery(jpql, User.class).getResultList();
        for (User userentity : resultList) {
            log.info("findUser = " + userentity.toString());
            log.info("findUser createdDate = " + userentity.getCreatedDate());
            log.info("findUser modifiedDate = " + userentity.getModifiedDate());
        }

        TypedQuery<User> query = entityManager.createQuery("SELECT m FROM User m where email = :email", User.class);
        query.setParameter("email", "devsunset@gmail.com");
        query.setFirstResult(1);    // 조회 시작 위치
        query.setMaxResults(10);    // 조회할 데이터 수
        List<User> resultList1 = query.getResultList();
//        query.getSingleResult() : 결과가 정확히 하나일 때 사용
//                - 결과가 없으면 javax.persistence.NoResultException 예외 발생
//                - 결과가 1보다 많으면 javax.persistence.NonUniqueResultException 예외 발생
        for (User userentity : resultList1) {
            log.info("findUser = " + userentity.toString());
        }

        Query querySub = entityManager.createQuery("SELECT m.email, m.birthYear FROM User m");

        List resultList2 = querySub.getResultList();
        for (Object o : resultList2) {
            Object[] result = (Object[]) o;
            log.info("email = " + result[0]);
            log.info("birthYear = " + result[1]);
        }

        List<Object[]> result1 = entityManager.createQuery("SELECT m, t FROM User m JOIN m.userTech t").getResultList();
        for (Object[] row : result1) {
            User userList = (User) row[0];
            UserTech userTechList = (UserTech) row[1];
            log.info(userList.toString());
            log.info(userTechList.toString());
        }

        // Question ? - no data
        List<User> result2 = entityManager.createQuery("SELECT m FROM User m JOIN fetch m.userTech").getResultList();
        for (User userfetch : result2 ) {
            log.info("-------------"+userfetch.toString());
            log.info("-------------"+userfetch.getEmail());
            log.info("-------------"+userfetch.getUserTech().toString());
        }

        // Query
        User queryFindUser = UserRepository.queryFindByEmail("devsunset@gmail.com");
        log.info("queryFindUser = " + queryFindUser.toString());

        // Native Query
        String sql = "SELECT * FROM User  WHERE email = ?";
        Query nativeQuery = entityManager.createNativeQuery(sql, User.class)
                .setParameter(1, "devsunset@gmail.com");
        List<User> resultListNative = nativeQuery.getResultList();
        for (User Userentity : resultListNative) {
            log.info("resultListNative = " + Userentity.toString());
        }

//        QueryDSL
//        https://velog.io/@junho918/Querydsl-%EC%8B%A4%EC%A0%84-Querydsl
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        QUser m = new QUser("m");

        User findQdslUser = queryFactory
                .select(m)
                .from(m)
                .where(m.email.eq("devsunset@gmail.com"))
                .fetchOne();

        log.info("findQdslUser = "+findQdslUser.toString());
        Assertions.assertThat(findQdslUser.getEmail()).isEqualTo("devsunset@gmail.com");
    }
}
