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
    private Long techId;

    @OneToMany(mappedBy = "tech")
    private List<MemberTech> memberTech = new ArrayList<>();

    // To-Do - Add Field
}
