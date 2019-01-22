package com.fmi.findmeabuddy.handler.user;

import com.fmi.findmeabuddy.domain.Account;
import com.fmi.findmeabuddy.exception.HttpException;
import com.fmi.findmeabuddy.matching.UserMatch;
import com.fmi.findmeabuddy.repository.AccountRepository;
import com.fmi.findmeabuddy.util.AccountRetriever;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

@RestController
public class UserMatchHandler {

    private final AccountRepository accountRepository;
    private final AccountRetriever accountRetriever;

    private static DecimalFormat df2 = new DecimalFormat(".##");

    public UserMatchHandler(AccountRepository accountRepository, AccountRetriever accountRetriever) {
        this.accountRepository = accountRepository;
        this.accountRetriever = accountRetriever;
    }

    @GetMapping(value = "/user/match", produces=MediaType.APPLICATION_JSON_VALUE)
    public String getById(@RequestHeader("Authorization") String bearerToken ) {

        Account currentUser = accountRetriever.retrieve(bearerToken)
                .orElseThrow(() -> new HttpException("No account found", HttpStatus.UNPROCESSABLE_ENTITY));

        BigDecimal latitude = currentUser.getProfile().getCity().getLatitude();
        BigDecimal longitude = currentUser.getProfile().getCity().getLatitude();

        //the values here should be around 0.6f
        BigDecimal offset = new BigDecimal(0.6);
        List<Account> closest_users = this.accountRepository.findByLocation(latitude.add(offset),
                latitude.subtract(offset), longitude.add( offset), longitude.subtract(offset));

        String jsonString = "";

        UserMatch matchobj = new UserMatch();
        for (int i = 0; i < closest_users.size(); i++) {
            String email="", hobby_one="", hobby_two="", hobby_three="", hobby_four="", hobby_five="", birthday="", gender="", city="", account_id="";

            Account matchinguser = closest_users.get(i);

            if(!currentUser.getAccountId().equals(matchinguser.getAccountId())) {

                double lscore = matchobj.StringtoInt(currentUser, matchinguser);
                // { "name":"John", "age":30, "car":null }

                String jsonEntry;
                jsonEntry =
                        "{" + "\"account_id\"" + ":" + account_id + "," +
                                "\"email\"" + ":\"" + email + "\"," +
                                "\"hobby_one\"" + ":\"" + hobby_one + "\"," +
                                "\"hobby_two\"" + ":\"" + hobby_two + "\"," +
                                "\"hobby_three\"" + ":\"" + hobby_three + "\"," +
                                "\"hobby_four\"" + ":\"" + hobby_four + "\"," +
                                "\"hobby_five\"" + ":\"" + hobby_five+ "\"," +
                                "\"birthday\"" + ":\"" + birthday + "\"," +
                                "\"gender\"" + ":\"" + gender + "\"," +
                                "\"city\"" + ":\"" + city + "\","  +
                                "\"match_score\"" +":"+ df2.format(lscore*100) + "}";
                if (i < closest_users.size()-1) {
                    jsonString = jsonString + "" + jsonEntry + ",";
                } else {
                    jsonString = jsonString + "" + jsonEntry;
                }

            }

        }
        jsonString = "["+jsonString+"]";

        return jsonString;
    }

    private List<String[]> getHobbies(long id) {
        return accountRepository.getHobbyList(id);
    }

    private List<String[]> getProfileData(long id) {
        return accountRepository.getUserProfileData(id);
    }
}
