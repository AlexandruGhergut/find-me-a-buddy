package com.fmi.findmeabuddy.domain;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder(toBuilder = true)
public class MatchedUser {

    private Account account;

    private Double matchScore;

    @Override
    public String toString() {
        return "MatchedUser{" +
                "account=" + account +
                ", matchScore=" + matchScore +
                '}';
    }
}
