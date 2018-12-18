package com.fmi.findmeabuddy.login_Service;

import com.fmi.findmeabuddy.domain.Account;
import com.fmi.findmeabuddy.new_repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service("loginService")
public class LoginService {
    private UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public LoginService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Account findUserByEmail(String email){
        return userRepository.findByEmail(email);
    }
}
