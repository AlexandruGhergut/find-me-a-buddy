package com.fmi.findmeabuddy.loginController;

import com.fmi.findmeabuddy.domain.Account;
import com.fmi.findmeabuddy.login_Service.LoginService;
import com.fmi.findmeabuddy.new_repository.UserRepository;
import com.fmi.findmeabuddy.repository.AccountRepository;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private Account account;

    public LoginController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping(value = "/login",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)

    public Account loginUser(String email, String enteredPassword) {
        Account account = userRepository.findByEmail(email);
        String hashedPassword = account.getPassword();
        if (account != null && passwordEncoder.matches(enteredPassword, hashedPassword)) {
            return account;
        }
        return null;
    }
}


