package com.example.user.myapplication.model.entity;

import static com.example.user.myapplication.model.StringUtil.nonNull;

public class UserCredentials {
    private String username;
    private String password;

    public UserCredentials(String username, String password) {
        this.username = nonNull(username);
        this.password = nonNull(password);
    }
}
