package com.fmi.findmeabuddy.handler.user;

import com.fmi.findmeabuddy.domain.Account;
import com.fmi.findmeabuddy.exception.HttpException;
import com.fmi.findmeabuddy.repository.AccountRepository;
import com.fmi.findmeabuddy.util.AccountRetriever;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserAccountHandler {

    private final AccountRetriever accountRetriever;
    private final AccountRepository accountRepository;

    public UserAccountHandler(AccountRetriever accountRetriever, AccountRepository accountRepository) {
        this.accountRetriever = accountRetriever;
        this.accountRepository = accountRepository;
    }

    @GetMapping(value = "/user/account", produces= MediaType.APPLICATION_JSON_VALUE)
    public Account getAccount(@RequestHeader("Authorization") String bearerToken ) {

        return accountRetriever.retrieve(bearerToken)
                .orElseThrow(() -> new HttpException("No account found", HttpStatus.UNPROCESSABLE_ENTITY));
    }

    @GetMapping(value = "/user/account/{accountId}", produces= MediaType.APPLICATION_JSON_VALUE)
    public Account getAccountById(@RequestHeader("Authorization") String bearerToken,
                                  @PathVariable Long accountId) {

        // we validate that the request is coming from a logged in user
        if (!accountRetriever.retrieve(bearerToken).isPresent()) {
            throw new HttpException("No account found", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        return accountRepository.findById(accountId)
                .orElseThrow(() -> new HttpException("No account found", HttpStatus.UNPROCESSABLE_ENTITY));
    }
}
