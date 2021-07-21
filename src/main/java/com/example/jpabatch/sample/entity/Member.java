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

    @Lob
    private String introduce;

    @Enumerated(EnumType.STRING)
    private GenderType genderType;

    @Enumerated(EnumType.STRING)
    private RoleType roleType = RoleType.USER;

    // To-Do - Add Field
}

enum GenderType{
    MAN, WOMAN
}

enum RoleType {
    ADMIN, USER
}