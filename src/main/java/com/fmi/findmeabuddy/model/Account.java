package com.fmi.findmeabuddy.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long accountId;

    @Column(name = "email", unique = true)
    private String email;

    @JsonProperty(access = WRITE_ONLY)
    private String password;

    public String getPassword() {
        return password;
    }
}
