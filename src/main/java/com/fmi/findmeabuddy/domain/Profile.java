package com.fmi.findmeabuddy.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.util.Set;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder(toBuilder = true)

public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_id")
    private Long profileId;

    @OneToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id")
    @JsonProperty(access = WRITE_ONLY)
    private Account account;

    private Date birthday;

    private Character gender;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "city_id")
    private City city;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "ProfileHobby",
        joinColumns = @JoinColumn(name = "profile_id"),
        inverseJoinColumns = @JoinColumn(name = "hobby_id")
    )
    private Set<Hobby> hobbies;
}
