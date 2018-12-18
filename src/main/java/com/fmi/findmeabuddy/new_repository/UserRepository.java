package com.fmi.findmeabuddy.new_repository;


import com.fmi.findmeabuddy.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<Account, Long> {
    Account findByEmail(String email);
}
