package com.example.jpabatch.sample.repository;

import com.example.jpabatch.sample.entity.QUser;
import com.example.jpabatch.sample.entity.Study;
import com.example.jpabatch.sample.entity.Tech;
import com.example.jpabatch.sample.entity.User;
import com.example.jpabatch.sample.entity.UserStudy;
import com.example.jpabatch.sample.entity.UserTech;
import com.querydsl.jpa.impl.JPAQueryFactory;
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
        String email = "devsunset@gmail.com";
        User User = new User();
        User.setEmail(email);
        User.setNickName("devsunset");
        User.setBirthYear(1978);

        //Create
        User savedUser = UserRepository.save(User);
        assertEquals(User.getEmail(), savedUser.getEmail());
        log.info("=== Create User = " + savedUser.toString());

        //Read
        User findByEmail = UserRepository.findByEmail(email);
        assertEquals(email, findByEmail.getEmail());
        log.info("=== Read User = " + findByEmail.toString());

        //Update
        findByEmail.setGithub("https://github.com/devsunset");
        savedUser = UserRepository.save(findByEmail);
        assertEquals("https://github.com/devsunset", savedUser.getGithub());
        log.info("=== Update User = " +savedUser.toString());

        List<User> findUser = UserRepository.findAll();
        for (User Userentity : findUser) {
            //Delete
            UserRepository.delete(Userentity);
            List<User> findUserSub = UserRepository.findAll();
            assertEquals(0, findUserSub.size());
            log.info(" Delete User :  size : "+findUserSub.size());
        }
    }

    @Test
    public void User_complex_test() {
        User user = new User();
        user.setEmail("devsunset@gmail.com");
        user.setNickName("devsunset");
        user.setBirthYear(1978);
        User savedUser = UserRepository.save(user);
        assertEquals(user.getEmail(), savedUser.getEmail());

        User userSub = new User();
        userSub.setEmail("k2h100@naver.com");
        userSub.setNickName("sunset");
        userSub.setBirthYear(2011);
        User savedUserSub = UserRepository.save(userSub);
        assertEquals(userSub.getEmail(), savedUserSub.getEmail());

        Tech tech = new Tech();
        tech.setCategory("LANGUAGE");
        tech.setItem("JAVA");
        tech.setIcon("icon_location1");
        Tech saveTech = techRepository.save(tech);
        assertEquals(tech.getItem(), saveTech.getItem());

        Tech techSub = new Tech();
        techSub.setCategory("DB");
        techSub.setItem("H2");
        techSub.setIcon("icon_location2");
        Tech saveTechSub = techRepository.save(techSub);
        assertEquals(techSub.getItem(), saveTechSub.getItem());

        Study study = new Study();
        study.setSubject("programming study");
        study.setTitle("Hello World");
        Study savedStudy = studyRepository.save(study);
        assertEquals(study.getTitle(), savedStudy.getTitle());

        Study studySub = new Study();
        studySub.setSubject("toy project");
        studySub.setTitle("Web Application");
        Study savedStudySub = studyRepository.save(studySub);
        assertEquals(studySub.getTitle(), savedStudySub.getTitle());

        UserTech userTech = new UserTech();
        userTech.setUser(user);
        userTech.setTech(tech);
        UserTech savedUserTech =  UserTechRepository.save(userTech);
        assertEquals(userTech.getUser(), savedUserTech.getUser());
        assertEquals(userTech.getTech(), savedUserTech.getTech());

        UserTech userTechSub = new UserTech();
        userTechSub.setUser(userSub);
        userTechSub.setTech(techSub);
        UserTech savedUserTechSub =  UserTechRepository.save(userTechSub);
        assertEquals(userTechSub.getUser(), savedUserTechSub.getUser());
        assertEquals(userTechSub.getTech(), savedUserTechSub.getTech());

        UserStudy userStudy = new UserStudy();
        userStudy.setUser(user);
        userStudy.setStudy(study);
        UserStudy savedUserStudy = UserStudyRepository.save(userStudy);
        assertEquals(userStudy.getUser(), savedUserStudy.getUser());
        assertEquals(userStudy.getStudy(), savedUserStudy.getStudy());

        UserStudy userStudySub = new UserStudy();
        userStudySub.setUser(userSub);
        userStudySub.setStudy(studySub);
        UserStudy savedUserStudySub = UserStudyRepository.save(userStudySub);
        assertEquals(userStudySub.getUser(), savedUserStudySub.getUser());
        assertEquals(userStudySub.getStudy(), savedUserStudySub.getStudy());

        log.info("========================== TEST DATA LODA SUCCESS ========================================");

        List<User> findUser = UserRepository.findAll();
        for (User userentity : findUser) {
            log.info("=== JpaRepository User = " + userentity.toString());
            log.info("=== JpaRepository createdDate = " + userentity.getCreatedDate());
            log.info("=== JpaRepository modifiedDate = " + userentity.getModifiedDate());
        }

        String jpql = "select m from User as m where m.email = 'devsunset@gmail.com'";
        List<User> resultList = entityManager.createQuery(jpql, User.class).getResultList();
        for (User userentity : resultList) {
            log.info("=== JPQL User = " + userentity.toString());
            log.info("=== JPQL createdDate = " + userentity.getCreatedDate());
            log.info("=== JPQL modifiedDate = " + userentity.getModifiedDate());
        }

        TypedQuery<User> query = entityManager.createQuery("SELECT m FROM User m where email = :email", User.class);
        query.setParameter("email", "devsunset@gmail.com");
        query.setFirstResult(1);    // 조회 시작 위치
        query.setMaxResults(10);    // 조회할 데이터 수
        List<User> resultList1 = query.getResultList();
        for (User userentity : resultList1) {
            log.info("=== TypedQuery User = " + userentity.toString());
        }

        Query querySub = entityManager.createQuery("SELECT m.email, m.birthYear FROM User m");
        List resultList2 = querySub.getResultList();
        for (Object o : resultList2) {
            Object[] result = (Object[]) o;
            log.info("=== Query email = " + result[0]);
            log.info("=== Query birthYear = " + result[1]);
        }

        List<Object[]> result1 = entityManager.createQuery("SELECT m, t FROM User m JOIN m.userTech t").getResultList();
        for (Object[] row : result1) {
            User userList = (User) row[0];
            UserTech userTechList = (UserTech) row[1];
            log.info("=== Join Object User = " +userList.toString());
            log.info("=== Join Object UserTech = " +userTechList.toString());
        }

        List<User> result2 = entityManager.createQuery("SELECT m FROM User m JOIN m.userTech").getResultList();
        log.error("------------------- check ----------------- why no data ?");
        for (User userfetch : result2 ) {
            log.info("=== Join  User = "+userfetch.toString());
            log.info("=== Join  UserTech = "+userfetch.getUserTech().toString());
        }

        User queryFindUser = UserRepository.queryFindByEmail("devsunset@gmail.com");
        log.info("=== JPARepository Query User = " + queryFindUser.toString());

        String sql = "SELECT * FROM User  WHERE email = ?";
        Query nativeQuery = entityManager.createNativeQuery(sql, User.class)
                .setParameter(1, "devsunset@gmail.com");
        List<User> resultListNative = nativeQuery.getResultList();
        for (User Userentity : resultListNative) {
            log.info("=== Native Query User = " + Userentity.toString());
        }

        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        QUser m = new QUser("m");

        User findQdslUser = queryFactory
                .select(m)
                .from(m)
                .where(m.email.eq("devsunset@gmail.com"))
                .fetchOne();

        log.info("=== QueryDSL User = " +findQdslUser.toString());
    }
}
