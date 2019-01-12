package com.fmi.findmeabuddy.repository;

import com.fmi.findmeabuddy.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigInteger;
import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {

    //search for users around our user
    @Query(value = "SELECT a.account_id as account_id FROM Account a, Profile b, Cities c WHERE a.account_id = b.account_id AND b.city_name = c.name AND c.latitude < :latup AND c.latitude > :latdw AND c.longitude < :lonup AND c.longitude > :londw", nativeQuery = true)
    List<BigInteger> findByLocation(@Param("latup") float latup, @Param("latdw") float latdw,
                                    @Param("lonup") float lonup, @Param("londw") float londw);


    //get latitude and longitude for our user
    @Query(value = "SELECT c.latitude, c.longitude FROM Account a, Profile b, Cities c WHERE c.name = b.city_name AND a.account_id = b.account_id AND a.account_id = :accid", nativeQuery = true)
    List<Float[]> getLatAndLong(@Param("accid") float accid);

    //get hobbies of our user
    @Query(value = "SELECT b.hobby_one, b.hobby_two, b.hobby_three, b.hobby_four, b.hobby_five FROM Profile b WHERE b.account_id = :accid", nativeQuery = true)
    List<String[]> getHobbyList(@Param("accid") long accid);

    //get profile of user for json
    @Query(value = "SELECT a.email, b.hobby_one, b.hobby_two, b.hobby_three, b.hobby_four, b.hobby_five, b.birthday, b.gender, b.city_name, a.account_id FROM Account a, Profile b WHERE a.account_id = b.account_id AND b.account_id = :accid", nativeQuery = true)
    List<String[]> getUserProfileData(@Param("accid") long accid);

}
