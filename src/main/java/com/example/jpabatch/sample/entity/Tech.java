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
public class Tech extends BaseEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long tech_id;

    @OneToMany(mappedBy = "tech")
    private List<UserTech> users = new ArrayList<UserTech>();

    private String category;

    private String item;

    private String icon;

    // To-Do - Add Field
}
