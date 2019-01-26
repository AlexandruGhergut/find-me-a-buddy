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
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UserMatchHandler {

    private final AccountRepository accountRepository;
    private final AccountRetriever accountRetriever;

    public UserMatchHandler(AccountRepository accountRepository, AccountRetriever accountRetriever) {
        this.accountRepository = accountRepository;
        this.accountRetriever = accountRetriever;
    }

    @GetMapping(value = "/user/match", produces=MediaType.APPLICATION_JSON_VALUE)
    public List<MatchedUser> getById(@RequestHeader("Authorization") String bearerToken ) {

        Account currentUser = accountRetriever.retrieve(bearerToken)
                .orElseThrow(() -> new HttpException("No account found", HttpStatus.UNPROCESSABLE_ENTITY));

        BigDecimal latitude = currentUser.getProfile().getCity().getLatitude();
        BigDecimal longitude = currentUser.getProfile().getCity().getLatitude();

        //the values here should be around 0.6f
        BigDecimal offset = new BigDecimal(0.6);
        List<Account> closest_users = this.accountRepository.findByLocation(latitude.add(offset),
                latitude.subtract(offset), longitude.add(offset), longitude.subtract(offset));

        UserMatch matchobj = new UserMatch();

        return closest_users.stream()
                .map(closest_user -> {
                    double lscore = matchobj.StringtoInt(currentUser, closest_user);
                    return MatchedUser.builder()
                                    .account(closest_user)
                                    .matchScore(lscore*100)
                                    .build();
                        })
                .filter(matchedUser -> matchedUser.getMatchScore() > 60)
                .collect(Collectors.toList());
    }
}
