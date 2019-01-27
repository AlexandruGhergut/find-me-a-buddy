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
    @Query(value = "SELECT * FROM Account a " +
            "LEFT JOIN Profile b on a.account_id = b.account_id " +
            "LEFT JOIN City c on b.city_id = c.city_id " +
            "WHERE c.latitude <= :maxLatitude " +
            "AND c.latitude >= :minLatitude " +
            "AND c.longitude <= :maxLongitude " +
            "AND c.longitude >= :minLongitude", nativeQuery = true)
    List<Account> findByLocation(@Param("minLatitude") BigDecimal minLatitude,
                                 @Param("maxLatitude") BigDecimal maxLatitude,
                                 @Param("minLongitude") BigDecimal minLongitude,
                                 @Param("maxLongitude") BigDecimal maxLongitude);

}
