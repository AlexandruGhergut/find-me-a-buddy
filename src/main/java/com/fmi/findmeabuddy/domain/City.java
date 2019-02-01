package com.fmi.findmeabuddy.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder(toBuilder = true)
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "city_id")
    private Integer cityId;

    @Column(name = "region_id")
    private Integer regionId;

    @ManyToOne
    @JoinColumn(name = "country_id")
    @JsonProperty(access = WRITE_ONLY)
    private Country country;

    private BigDecimal latitude;

    private BigDecimal longitude;

    private String name;

    @Override
    public String toString() {
        return "City{" +
                "cityId=" + cityId +
                ", regionId=" + regionId +
                ", country=" + country +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", name='" + name + '\'' +
                '}';
    }
}
