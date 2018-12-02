package com.fmi.findmeabuddy.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

@Entity
@Data
@Builder(toBuilder = true)
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long accountId;

    private String email;

    private String password;

    @Column(name = "created_at", updatable = false)
    @NotNull
    private ZonedDateTime createdAt;

    @Column(name = "created_at", updatable = false)
    @NotNull
    private ZonedDateTime updatedAt;
}
