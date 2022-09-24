package com.example.demo.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class UserModel {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private List<RoleModel> role;
}

