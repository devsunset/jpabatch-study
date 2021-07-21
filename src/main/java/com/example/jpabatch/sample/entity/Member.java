package com.example.jpabatch.sample.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
/*
  @Entity 주의사항
- 기본 생성자는 필수 (JPA가 엔티티 객체 생성 시 기본 생성자를 사용)
- final 클래스, enum, interface, inner class 에는 사용할 수 없음
- 저장할 필드에 final 사용 불가
*/
@Entity
/*
@Table
Name : 매핑할 테이블 이름 (default. 엔티티 이름 사용)
        Catalog : catalog 기능이 있는 DB에서 catalog 를 매핑 (default. DB 명)
        Schema : schema 기능이 있는 DB에서 schema를 매핑
        uniqueConstraints : DDL 생성 시 유니크 제약조건을 만듦
        스키마 자동 생성 기능을 사용해서 DDL을 만들 때만 사용
*/
@Table(name="MEMBER", uniqueConstraints = {@UniqueConstraint(name = "NAME_AGE_UNIQUE", columnNames = {"NAME", "AGE"} )})
public class Member {
    /*
    @GeneratedValue
    <기본 키 생성 전략>
    - 직접 할당 : 기본 키를 애플리케이션에 직접 할당

    - 자동 생성 : 대리 키 사용 방식
        * IDENTITY : 기본 키 생성을 데이터베이스에 위임 (= AUTO_INCREMENT)
        @Id
        @GeneratedValue(strategy=GenerationType.IDENTITY)
        @Column(name = "ID")
        private Long id;
        - Statement.getGeneratedKeys() 를 사용해서 데이터를 저장함과 동시에 생성된 기본 키 값을 얻어 올 수 있음.

        * SEQUENCE : 데이터베이스 시퀀스를 사용해서 기본 키를 할당,
        데이터베이스 시퀀스에서 식별자 값을 획득한 후 영속성 컨텍스트에 저장
        유일한 값을 순서대로 생성(오라클, PostgreSQL, DB2, H2)

        * TABLE : 키 생성 테이블을 사용
        키 생성 전용 테이블 하나를 만들고 여기에 이름과 값으로 사용할 컬럼을 만들어
        데이터베이스 시퀀스를 흉내내는 전략. 테이블을 사용하므로 모든 데이터베이스에 적용 가능

        * AUTO : 선택한 데이터베이스 방언에 따라 방식을 자동으로 선택(Default)
        Ex) 오라클 DB 선택 시 SEQUENCE, MySQL DB 선택 시 IDENTITY 사용
    */
    // 기본키 매핑
    @Id
    @Column(name = "ID")
    private String id;

    /*
    @Column
    name : 필드와 매핑할 테이블 컬럼 이름 (default. 객체의 필드 이름)
    nullable (DDL) : null 값의 허용 여부 설정, false 설정 시 not null (default. true)
                     @Column 사용 시 nullable = false 로 설정하는 것이 안전
    unique (DDL) : @Table 의 uniqueConstraints와 같지만 한 컬럼에 간단히 유니크 제약조건을 적용
    columnDefinition (DDL) : 데이터베이스 컬럼 정보를 직접 줄 수 있음, default 값 설정
                            (default. 필드의 자바 타입과 방언 정보를 사용해 적절한 컬럼 타입을 생성)
    length (DDL) : 문자 길이 제약조건, String 타입에만 사용 (default. 255)
    percision, scale (DDL) : BigDecimal, BigInteger 타입에서 사용. 아주 큰 숫자나 정밀한 소수를 다룰 때만 사용
                             (default. precision = 19, scale = 2)
    */
    // not null, varchar(10)
    @Column(name = "NAME", nullable = false, length = 10)
    private String username;

    private Integer age;

    /*
    @Enumerated
    value : EnumType.ORDINAL : enum 순서를 데이터베이스에 저장
            EnumType.STRING  : enum 이름을 데이터베이스에 저장
            (default. EnumType.ORDINAL)
    */
    // Java 의 enum 사용
    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    /*
    @Temporal
    value :  TemporalType.DATE : 날짜, 데이터베이스 data 타입과 매핑 (2020-12-18)
             TemporalType.TIME : 시간, 데이터베이스 time 타입과 매핑 (23:36:33)
             TemporalType.TIMESTAMP : 날짜와 시간, 데이터베이스 timestamp 타입과 매핑 (2020-12-18 23:36:33)
             (default. TemporalType은 필수로 지정)
             @Temporal 을 생략하면 자바의 Date와 가장 유사한 timestamp로 정의
    */
    // Java 의 날짜 타입
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    // @Lob CLOB, BLOC 타입 매핑 - 필드 타입이 문자면 CLOB로 나머지는 BLOB로 매핑
    @Lob
    private String description;

    // @Transient 데이터베이스에 저장하지 않고 조회하지도 않음
    @Transient
    private String temp;

   /*
    @Access
    JPA가 엔티티 데이터에 접근하는 방식을 지정
    - 필드 접근 : AccessType.FIELD 로 지정
    필드에 직접 접근 (private도 접근 가능)
    - 프로퍼티 접근: AccessType.PROPERTY 로 지정
    접근자 Getter를 사용
   */

    /*
    @ManyToOne
    다대일(N:1) 관계 매핑 정보 연관관계 매핑 시 다중성 어노테이션은 필수

    optional : false 설정 시 연관된 엔티티가 항상 있어야 함. (default. true)
                - EAGER 설정의 조인 전략
                @ManyToOne, @OneToOne
                    - (optional = false) : 내부 조인
                    - (optional = true) : 외부 조인
                @OneToMany, @ManyToMany
                    - (optional = false) : 외부 조인
                    - (optional = true) : 외부 조인

    fetch : global fetch 전략 설정
                @ManyToOne, @OneToOne(fetch = FetchType.EAGER) "즉시 로딩"
                    - 엔티티를 조회할 때 연관된 엔티티도 함께 조회
                    - 연관된 엔티티를 즉시 조회, hibernate는 가능하면 SQL 조인을 사용해서 한 번에 조회
                    - 객체가 계속 연결되어있을 경우 무한루프로 stack overflow 가능성..
                @OneToMany, ManyToMany(fetch = FetchType.LAZY) "지연 로딩"
                    - 연관된 엔티티를 프록시로 조회, 프록시를 실제 사용할 때 초기화하면서 데이터베이스 조회
                    - 프록시 객체 : 지연 로딩 기능을 위해 실제 엔티티 객체 대신 데이터베이스 조회를 지연할 수 있는 가짜 객체

    cascade : 영속성 전이 기능 사용
                @OneToMany(mappedBy = "parent", cascade = CascadeType.PERSIST)
                    - 부모를 영속화할 때 연관된 자식들도 함께 영속화
                    - 부모 엔티티를 저장할 때 자식 엔티티도 함께 저장
                    , cascade = CascadeType.REMOVE
                    - 부모 엔티티만 삭제하면 연관된 자식 엔티티도 함께 삭제
                    - CasecadeType 의 다양한 옵션
                    ALL,  // 모두 적용
                    PERSIST,  // 영속
                    MERGE,  // 병합
                    REMOVE,  // 삭제
                    REFRESH,
                    DETACH

    orphanRemoval : 부모 엔티티의 컬렉션에서 자식 엔티티의 참조만 제거하면 자식 엔티티가 자동으로 삭제(true)
                    - 참조하는 곳이 하나일 때만 사용 --> @OneToOne, @OneToMany
                    - CascadeType.Remove 설정과 동일

    * cascadeType.All + orphanRemoval = true 일 경우, 부모 엔티티를 통해서 자식의 생명주기를 관리할 수 있음.
                    - 자식을 저장하려면 부모에 등록(CASECADE)
                    - 자식을 삭제하려면 부모에서 제거(removeObject)


    @JoinColumn(name="TEAM_ID")
    외래 키 매핑
    name : 매핑할 외래 키 이름
           default. [필드명]_[참조하는 테이블의 기본키 컬럼명]
    referencedColumnName : 외래 키가 참조하는 대상 테이블의 컬럼명
           default. 참조하는 테이블의 기본 키 컬럼명
    foreignKey (DDL) : 외래 키 제약조건을 직접 지정 (테이블 생성시에만 사용)
    unique    : @Column 속성과 동일
    nullable
    insertable
    updatable
    columnDefinition
    table
    * @JoinColumn 생략 시 외래 키를 찾을 때 기본 전략을 사용
        [필드명]_[참조하는 테이블의 컬럼명]
     */
    @ManyToOne
    @JoinColumn(name="TEAM_ID")
    private Team team;

    // 연관관계 설정
    public void setTeam(Team team) {
        // 기존 팀과 관계를 제거
        if (this.team != null) {
            this.team.getMembers().remove(this);
        }
        this.team = team;
        team.getMembers().add(this);
    }

}

enum RoleType {
    ADMIN, USER
}