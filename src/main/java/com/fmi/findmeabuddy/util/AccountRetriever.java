package com.fmi.findmeabuddy.util;

import com.fmi.findmeabuddy.domain.Account;
import com.fmi.findmeabuddy.repository.AccountRepository;
import com.fmi.findmeabuddy.security.JwtTokenProvider;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountRetriever {

    private final AccountRepository accountRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public AccountRetriever(AccountRepository accountRepository, JwtTokenProvider jwtTokenProvider) {
        this.accountRepository = accountRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public Optional<Account> retrieve(String bearerToken) {
        String tokenValue = bearerToken.substring(7);
        String email = jwtTokenProvider.getUsername(tokenValue);

        return accountRepository.findByEmail(email);
    }
}
