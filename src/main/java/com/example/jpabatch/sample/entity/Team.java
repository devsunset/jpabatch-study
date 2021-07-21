package com.example.jpabatch.sample.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Team {
    @Id
    @Column(name = "TEAM_ID")
    private String id;

    private String name;

    /*
    @OneToMany 의 mappedBy 속성은 양방향 매핑일 때 사용,
    반대쪽 매핑의 필드 이름값으로 설정

    객체에서의 연관관계는 "서로 다른 단방향 연관관계 2개" 와 같다.
    단, 테이블은 외래 키 하나로 두 테이블의 연관관계를 관리한다.

    엔티티를 양방향 연관관계로 설정하면 객체의 참조는 둘인데 외래 키는 하나가 되므로, 둘 사이에 차이가 발생한다.
    => 이러한 차이를 JPA에서는 두 객체 연관관계 중 하나를 정해서, 테이블의 외래키를 연관관계의 주인으로 관리

    > 방향향 매핑의 규칙 : 연관관계의 주인
        - 두 연관관계 중 하나의 연관관계의 주인으로 정해야 함
        - 연관관계의 주인만이 데이터베이스 연관관계와 매핑되고 외래 키를 관리(등록, 수정, 삭제)할 수 있음
        - 주인이 아닌 쪽은 읽기만 가능
        - 연관관계 주인은 mappedBy 속성으로 정해줌

    * 주인은 mappedBy 속성을 사용하지 않음
    * 주인이 아니면 mappedBy 속성을 사용해서 속성의 값으로 연관관계의 주인을 지정해주어야 함
        - 연관관계의 주인을 정한다는 것은 "외래 키 관리자를 선택하는 것"
        - 외래키(FK) 가 있는 테이블 객체가 연관관계의 주인

    데이터베이스 테이블의 다대일, 일대다 관계에서는 항상 다 쪽이 외래 키를 가짐.
    다 쪽인 @ManyToOne은 항상 연관관계의 주인이 되므로 mappedBy 설정이 불가
    */
    @OneToMany(mappedBy = "team") // 연관관계의 주인이 아님 연관관계의 주인은 Member.team
    private List<Member> members = new ArrayList<Member>();
}

