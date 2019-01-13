package com.fmi.findmeabuddy.handler;

import com.fmi.findmeabuddy.exception.HttpException;
import com.fmi.findmeabuddy.matching.UserMatch;
import com.fmi.findmeabuddy.repository.AccountRepository;
import com.fmi.findmeabuddy.security.JwtTokenProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.List;

@RestController
public class UserMatchHandler {

    private final AccountRepository accountRepository;
    private final JwtTokenProvider jwtTokenProvider;

    private static DecimalFormat df2 = new DecimalFormat(".##");

    public UserMatchHandler(AccountRepository accountRepository, JwtTokenProvider jwtTokenProvider) {
        this.accountRepository = accountRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @GetMapping(value = "/user/match", produces=MediaType.APPLICATION_JSON_VALUE)
    public String getById(@RequestHeader("Authorization") String bearerToken ) {

        long currentUser = getAccountId(bearerToken);
        List<Float[]> list = this.accountRepository.getLatAndLong(currentUser);

        float latitude = 0f;
        float longitude = 0f;
        for (Float[] floats : list) {
            latitude = Float.parseFloat(String.valueOf(floats[0]));
            longitude = Float.parseFloat(String.valueOf(floats[1]));
        }

        //the values here should be around 0.6f
        List<BigInteger> closest_users = this.accountRepository.findByLocation(latitude + 5f, latitude - 5f, longitude + 5f, longitude - 5f);

        String jsonString = "";

        UserMatch matchobj = new UserMatch();
        for (int i = 0; i < closest_users.size(); i++) {
            String email="", hobby_one="", hobby_two="", hobby_three="", hobby_four="", hobby_five="", birthday="", gender="", city="", account_id="";

            List<String[]> user1 = getHobbies(currentUser);
            long matchinguser = Long.parseLong(closest_users.get(i).toString());
            List<String[]> user2 = getHobbies(matchinguser);

            if(!(currentUser==matchinguser)) {

                List<String[]> userdata = getProfileData(matchinguser);

                Iterator itr_data = userdata.iterator();
                while (itr_data.hasNext()) {
                    Object[] obj = (Object[]) itr_data.next();
                    email = String.valueOf(obj[0]);
                    hobby_one = String.valueOf(obj[1]);
                    hobby_two = String.valueOf(obj[2]);
                    hobby_three = String.valueOf(obj[3]);
                    hobby_four = String.valueOf(obj[4]);
                    hobby_five = String.valueOf(obj[5]);
                    birthday = String.valueOf(obj[6]);
                    gender = String.valueOf(obj[7]);
                    city = String.valueOf(obj[8]);
                    account_id = String.valueOf(obj[9]);
                }
                double lscore = matchobj.StringtoInt(user1, user2);
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

    private long getAccountId(String token) {
        String tokenValue = token.substring(7);
        String email = jwtTokenProvider.getUsername(tokenValue);

        return accountRepository.findByEmail(email)
                .orElseThrow(() -> new HttpException("No account found", HttpStatus.UNPROCESSABLE_ENTITY))
                .getAccountId();
    }

    private List<String[]> getHobbies(long id) {
        return accountRepository.getHobbyList(id);
    }

    private List<String[]> getProfileData(long id) {
        return accountRepository.getUserProfileData(id);
    }
}
