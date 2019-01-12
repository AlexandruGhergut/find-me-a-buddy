package com.fmi.findmeabuddy.handler;

import com.fmi.findmeabuddy.domain.Account;
import com.fmi.findmeabuddy.exception.HttpException;
import com.fmi.findmeabuddy.repository.AccountRepository;
import com.fmi.findmeabuddy.security.JwtTokenProvider;
import com.google.common.collect.ImmutableList;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginHandler {

    private final JwtTokenProvider jwtTokenProvider;

    private final AccountRepository accountRepository;
    private final AuthenticationManager authenticationManager;

    public LoginHandler(JwtTokenProvider jwtTokenProvider,
                        AccountRepository accountRepository,
                        AuthenticationManager authenticationManager) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.accountRepository = accountRepository;
        this.authenticationManager = authenticationManager;
    }


    @PostMapping(value = "/user/login",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String register(@RequestBody Account account) {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(account.getEmail(), account.getPassword())
            );

            Account completeAccount = accountRepository
                    .findByEmail(account.getEmail())
                    .orElseThrow(() -> new HttpException("Invalid username/password", HttpStatus.UNPROCESSABLE_ENTITY));

            String token = jwtTokenProvider.createToken(account.getEmail(),
                    ImmutableList.of(completeAccount.getRole()));

            return new JSONObject()
                    .put("token", token)
                    .toString();

        } catch (AuthenticationException e) {
            throw new HttpException("Error encountered on authentication", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
