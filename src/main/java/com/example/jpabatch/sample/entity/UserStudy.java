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
public class UserStudy extends BaseEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "email" , insertable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "study_id", insertable = false)
    private Study study;

    // To-Do - Add Field
}
