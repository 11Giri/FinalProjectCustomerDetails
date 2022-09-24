package com.example.demo.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
    @Setter
    @Entity
    @Table(name="User")
    public class User {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Integer userId;

        @Column
        private String firstName;

        @Column
        private String lastName;

        @Column
        private String email;

        @Column
        private String password;

    /*@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Role> roles = new HashSet<Role>();*/

        @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
        @JoinTable(name="user_role",
                joinColumns = {@JoinColumn(name="user_Id")},
                inverseJoinColumns = {@JoinColumn(name="role_Id")}
        )
        private Set<Role> roles = new HashSet<Role>();

    /*public void addRoles(Role role) {
        this.roles.add(role);
        role.setUser(this);
    }*/

        public void addRoles(Role role) {
            this.roles.add(role);
            role.getUserSet().add(this);
        }
    }

