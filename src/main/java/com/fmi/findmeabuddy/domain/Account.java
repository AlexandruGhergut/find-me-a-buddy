package com.fmi.findmeabuddy.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fmi.findmeabuddy.domain.tracing.DateTimeTrackableEntity;
import com.fmi.findmeabuddy.domain.tracing.DateTimeTrackableEntityListener;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.*;

@Entity
@EntityListeners(DateTimeTrackableEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder(toBuilder = true)
public class Account implements DateTimeTrackableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long accountId;

    @Column(name = "email", unique = true)
    private String email;
    
    @JsonProperty(access = WRITE_ONLY)
    private String password;

    @Column(name = "created_at", updatable = false)
    @NotNull
    private ZonedDateTime createdAt;
    
    @Column(name = "updated_at", updatable = false)
    @NotNull
    private ZonedDateTime updatedAt;
}
