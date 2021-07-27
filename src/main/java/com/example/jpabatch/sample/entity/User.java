package com.example.jpabatch.sample.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Getter
@Setter
@ToString
public class User extends BaseEntity {
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

    @OneToMany(mappedBy = "user")
    private List<UserTech> userTech = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<UserStudy> userStudy = new ArrayList<>();

    // To-Do - Add Field
}

enum GenderType{
    MAN, WOMAN
}

enum RoleType {
    ADMIN, USER
}