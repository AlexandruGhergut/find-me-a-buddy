package com.fmi.findmeabuddy.handler;

import com.fmi.findmeabuddy.domain.Account;
import com.fmi.findmeabuddy.repository.AccountRepository;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
public class RegistrationHandler {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    public RegistrationHandler(AccountRepository accountRepository,
                               PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    @PostMapping(value = "/user/register",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Account register(@RequestBody Account newAccount) {
        
        String encodedPassword = passwordEncoder.encode(newAccount.getPassword());
        newAccount.setPassword(encodedPassword);
        
        accountRepository.save(newAccount);
        
        return newAccount;
    }
}
