package com.fmi.findmeabuddy.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder(toBuilder = true)
public class Country {
    @Id
    @Column(name = "country_id", columnDefinition = "smallint(5)")
    private Integer countryId;

    @Column
    private String name;

    @Column
    private String code;
}
