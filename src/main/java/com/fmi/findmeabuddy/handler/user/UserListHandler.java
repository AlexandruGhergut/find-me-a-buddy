package com.fmi.findmeabuddy.handler.user;

import com.fmi.findmeabuddy.domain.Account;
import com.fmi.findmeabuddy.repository.AccountRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserListHandler {

    private final static int DEFAULT_PAGE_SIZE = 20;
    private final static int DEFAULT_PAGE_NUMBER = 0;

    private final AccountRepository accountRepository;

    public UserListHandler(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @GetMapping(value = "/user/",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    public List<Account> list(@RequestParam("page") Optional<Integer> pageNumber) {
        Pageable page = PageRequest.of(pageNumber.orElse(DEFAULT_PAGE_NUMBER), DEFAULT_PAGE_SIZE);

        return accountRepository.findAll(page).getContent();
    }
}
