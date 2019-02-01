package com.fmi.findmeabuddy.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder(toBuilder = true)
@EqualsAndHashCode
public class Hobby {
    @Id
    @Column(name = "hobby_id")
    private Integer hobbyId;

    private String name;

    @Override
    public String toString() {
        return "Hobby{" +
                "hobbyId=" + hobbyId +
                ", name='" + name + '\'' +
                '}';
    }
}
