package com.fmi.findmeabuddy.repository;

import com.fmi.findmeabuddy.domain.Account;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    @EntityGraph("AccountWithProfile")
    Optional<Account> findByEmail(String email);

    //search for users around our user
    @Query(value = "SELECT * FROM Account a LEFT JOIN Profile b on a.account_id = b.account_id LEFT JOIN City c on b.city_id = c.city_id WHERE c.latitude < :latup AND c.latitude > :latdw AND c.longitude < :lonup AND c.longitude > :londw", nativeQuery = true)
    List<Account> findByLocation(@Param("latup") BigDecimal latup, @Param("latdw") BigDecimal latdw,
                                 @Param("lonup") BigDecimal lonup, @Param("londw") BigDecimal londw);

}
