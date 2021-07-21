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
public class Study extends BaseTimeEntity {

    @Id
    @Column(name = "STUDY_ID")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    // To-Do - Add Field
}
