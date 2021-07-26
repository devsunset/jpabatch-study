package com.example.jpabatch.sample.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Getter
@Setter
@ToString
public class Member extends BaseEntity {
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

    @OneToMany(mappedBy = "member", fetch = FetchType.EAGER)
    private List<MemberTech> memberTech = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<MemberStudy> memberStudy = new ArrayList<>();

    // To-Do - Add Field
}

enum GenderType{
    MAN, WOMAN
}

enum RoleType {
    ADMIN, USER
}