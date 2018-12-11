package com.fmi.findmeabuddy.repository;

import com.fmi.findmeabuddy.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
