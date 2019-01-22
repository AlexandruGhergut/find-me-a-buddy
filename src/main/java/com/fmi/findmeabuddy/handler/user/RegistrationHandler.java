package com.fmi.findmeabuddy.handler.user;

import com.fmi.findmeabuddy.domain.Account;
import com.fmi.findmeabuddy.domain.Profile;
import com.fmi.findmeabuddy.domain.internal.Role;
import com.fmi.findmeabuddy.repository.AccountRepository;
import com.fmi.findmeabuddy.repository.ProfileRepository;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
public class RegistrationHandler {

    private final AccountRepository accountRepository;
    private final ProfileRepository profileRepository;

    private final PasswordEncoder passwordEncoder;

    public RegistrationHandler(AccountRepository accountRepository,
                               ProfileRepository profileRepository, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.profileRepository = profileRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    @PostMapping(value = "/user/register",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Account register(@RequestBody Account newAccount) {

        String encodedPassword = passwordEncoder.encode(newAccount.getPassword());
        newAccount.setPassword(encodedPassword);

        newAccount.setRole(Role.CLIENT);

        // TODO: Rewrite this hack
        Account savedAccount = accountRepository.save(newAccount);
        savedAccount.getProfile().setAccount(savedAccount);
        accountRepository.save(savedAccount);
        
        return savedAccount;
    }
}
