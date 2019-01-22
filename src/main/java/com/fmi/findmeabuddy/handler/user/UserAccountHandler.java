package com.fmi.findmeabuddy.handler.user;

import com.fmi.findmeabuddy.domain.Account;
import com.fmi.findmeabuddy.exception.HttpException;
import com.fmi.findmeabuddy.util.AccountRetriever;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserAccountHandler {

    private final AccountRetriever accountRetriever;

    public UserAccountHandler(AccountRetriever accountRetriever) {
        this.accountRetriever = accountRetriever;
    }

    @GetMapping(value = "/user/account", produces= MediaType.APPLICATION_JSON_VALUE)
    public Account getAccount(@RequestHeader("Authorization") String bearerToken ) {

        return accountRetriever.retrieve(bearerToken)
                .orElseThrow(() -> new HttpException("No account found", HttpStatus.UNPROCESSABLE_ENTITY));
    }
}
