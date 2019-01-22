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
    @Query(value = "SELECT a as account_id FROM Account a, Profile b, City c WHERE a.account_id = b.account_id AND b.city_name = c.name AND c.latitude < :latup AND c.latitude > :latdw AND c.longitude < :lonup AND c.longitude > :londw", nativeQuery = true)
    List<Account> findByLocation(@Param("latup") BigDecimal latup, @Param("latdw") BigDecimal latdw,
                                 @Param("lonup") BigDecimal lonup, @Param("londw") BigDecimal londw);


    //get latitude and longitude for our user
    @Query(value = "SELECT c.latitude, c.longitude FROM Account a, Profile b, City c WHERE c.name = b.city_name AND a.account_id = b.account_id AND a.account_id = :accid", nativeQuery = true)
    List<Float[]> getLatAndLong(@Param("accid") float accid);

    //get hobbies of our user
    @Query(value = "SELECT b.hobby_one, b.hobby_two, b.hobby_three, b.hobby_four, b.hobby_five FROM Profile b WHERE b.account_id = :accid", nativeQuery = true)
    List<String[]> getHobbyList(@Param("accid") long accid);

    //get profile of user for json
    @Query(value = "SELECT a.email, b.hobby_one, b.hobby_two, b.hobby_three, b.hobby_four, b.hobby_five, b.birthday, b.gender, b.city_name, a.account_id FROM Account a, Profile b WHERE a.account_id = b.account_id AND b.account_id = :accid", nativeQuery = true)
    List<String[]> getUserProfileData(@Param("accid") long accid);

}
