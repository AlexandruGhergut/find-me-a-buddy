package com.fmi.findmeabuddy.handler.user;

import com.fmi.findmeabuddy.domain.Account;
import com.fmi.findmeabuddy.domain.MatchedUser;
import com.fmi.findmeabuddy.exception.HttpException;
import com.fmi.findmeabuddy.matching.UserMatch;
import com.fmi.findmeabuddy.repository.AccountRepository;
import com.fmi.findmeabuddy.util.AccountRetriever;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class UserMatchHandler {

    private static final int MAX_MATCHED_USERS = 30;
    private static final String DEFAULT_LOCATION_OFFSET = "0.6";

    private final AccountRepository accountRepository;
    private final AccountRetriever accountRetriever;

    public UserMatchHandler(AccountRepository accountRepository, AccountRetriever accountRetriever) {
        this.accountRepository = accountRepository;
        this.accountRetriever = accountRetriever;
    }


    @GetMapping(value = "/user/match", produces=MediaType.APPLICATION_JSON_VALUE)
    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    public List<MatchedUser> getMatchedUsers(@RequestHeader("Authorization") String bearerToken,
                                             @RequestParam("maxUsers") Optional<Integer> maxUsers,
                                             @RequestParam("minScore") Optional<Integer> minScore,
                                             @RequestParam("locationOffset") Optional<String> locationOffset) {

        Account currentUser = accountRetriever.retrieve(bearerToken)
                .orElseThrow(() -> new HttpException("No account found", HttpStatus .UNPROCESSABLE_ENTITY));

        BigDecimal latitude = currentUser.getProfile().getCity().getLatitude();
        BigDecimal longitude = currentUser.getProfile().getCity().getLongitude();

        //the values here should be around 0.6f
        BigDecimal offset = new BigDecimal(locationOffset.orElse(DEFAULT_LOCATION_OFFSET));
        List<Account> closest_users = this.accountRepository.findByLocation(latitude.subtract(offset),
                latitude.add(offset), longitude.subtract(offset), longitude.add(offset));

        UserMatch matchobj = new UserMatch();

        Stream<MatchedUser> matchedUserStream = closest_users.stream()
                .filter(closest_user -> !closest_user.getAccountId().equals(currentUser.getAccountId()))
                .map(closest_user -> {
                    double lscore = matchobj.StringtoInt(currentUser, closest_user);
                    return MatchedUser.builder()
                                    .account(closest_user)
                                    .matchScore(lscore * 100)
                                    .build();
                        });

        if (minScore.isPresent()) {
            // get maxUsers random users who have matching scores higher or equal to the given minScore
            List<MatchedUser> matchedUsers = matchedUserStream
                    .filter(matchedUser -> matchedUser.getMatchScore() >= minScore.get())
                    .collect(Collectors.toList());
            Collections.shuffle(matchedUsers);

            return matchedUsers
                    .subList(0, Math.min(matchedUsers.size(), maxUsers.orElse(MAX_MATCHED_USERS)));
        } else {
            // get maxUsers users with highest matching scores
            return matchedUserStream
                    .sorted(Comparator.comparing(MatchedUser::getMatchScore).reversed())
                    .limit(maxUsers.orElse(MAX_MATCHED_USERS))
                    .collect(Collectors.toList());
        }
    }
}
