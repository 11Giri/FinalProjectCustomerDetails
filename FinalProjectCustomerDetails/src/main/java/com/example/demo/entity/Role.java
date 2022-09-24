package com.example.demo.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Setter
    @Getter
    @Entity
    @Table(name="Role")
    public class Role {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Integer roleId;

        @Column(name="role_name")
        private String name;

        @Column(name="dept_name")
        private String dept;

/*    @ManyToOne(fetch = FetchType.EAGER)
    private User user;*/

        @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
        private Set<User> userSet = new HashSet<User>();
    }

