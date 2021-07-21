package com.example.jpabatch.sample.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table
@Getter
@Setter
@ToString
public class Member extends BaseTimeEntity {
    @Id
    private String email;

    private String nickName;

    private Integer birthYear;

    private String homepage;

    private String github;

    @Enumerated(EnumType.STRING)
    private GenderType genderType;

    @Lob
    private String introduce;

    @Enumerated(EnumType.STRING)
    private RoleType roleType;
}

enum GenderType{
    MAN, WOMAN
}

enum RoleType {
    ADMIN, USER
}