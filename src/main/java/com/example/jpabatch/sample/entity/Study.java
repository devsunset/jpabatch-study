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
public class Study extends BaseEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long studyId;

    @OneToMany(mappedBy = "study")
    private List<UserStudy> userStudy = new ArrayList<>();

    private String subject;

    private String title;

    // To-Do - Add Field
}
