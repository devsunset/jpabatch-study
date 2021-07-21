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
public class Tech extends BaseTimeEntity {

    @Id
    @Column(name = "TECH_ID")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    // To-Do - Add Field
}
