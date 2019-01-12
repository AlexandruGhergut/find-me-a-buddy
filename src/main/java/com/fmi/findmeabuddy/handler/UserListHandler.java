package com.fmi.findmeabuddy.handler;

import com.fmi.findmeabuddy.domain.Account;
import com.fmi.findmeabuddy.matching.UserMatch;
import com.fmi.findmeabuddy.repository.AccountRepository;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

@RestController
public class UserListHandler {

    private final AccountRepository accountRepository;
    private static DecimalFormat df2 = new DecimalFormat(".##");

    public UserListHandler(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    long current_user = 0l;

    //to be checked
    public HashMap<String, String> mapJson(String email, String hobby_one, String hobby_two, String hobby_three, String hobby_four, String hobby_five, String birthday, String gender, String city, String score) {
        HashMap<String, String> map = new HashMap<>();
        map.put("email", email);
        map.put("hobby_one", hobby_one);
        map.put("hobby_two", hobby_two);
        map.put("hobby_three", hobby_three);
        map.put("hobby_four", hobby_four);
        map.put("hobby_five", hobby_five);
        map.put("birthday", birthday);
        map.put("gender", gender);
        map.put("city", city);
        map.put("score", score);
        return map;
    }

    public List<String[]> getHobbies(long id) {
        return accountRepository.getHobbyList(id);
    }
    public List<String[]> getProfileData(long id) { return accountRepository.getUserProfileData(id); }

    @GetMapping(value = "/user",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Account> list() {
        return accountRepository.findAll();
    }

    @GetMapping(value = "/matchuser/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
    public String/*ResponseEntity<Object>*/  getById(@PathVariable("id") int id) {

        current_user = id;
        List<Float[]> list = this.accountRepository.getLatAndLong(id);

        Float latitude = 0f;
        Float longitude = 0f;
        Iterator itr = list.iterator();
        while (itr.hasNext()) {
            Object[] obj = (Object[]) itr.next();
            latitude = Float.parseFloat(String.valueOf(obj[0]));
            longitude = Float.parseFloat(String.valueOf(obj[1]));
        }

        //the values here should be around 0.6f
        List<BigInteger> closest_users = this.accountRepository.findByLocation(latitude + 5f, latitude - 5f, longitude + 5f, longitude - 5f);

        String jsonString = "";

        List<String> aq = new ArrayList<String>();
        UserMatch matchobj = new UserMatch();
        for (int i = 0; i < closest_users.size(); i++) {
            String email="", hobby_one="", hobby_two="", hobby_three="", hobby_four="", hobby_five="", birthday="", gender="", city="", account_id="";

            List<String[]> user1 = getHobbies(current_user);
            long matchinguser = Long.parseLong(closest_users.get(i).toString());
            List<String[]> user2 = getHobbies(matchinguser);

            if(!(current_user==matchinguser)) {

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

                String jsonEntry = "";
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
        //return new ResponseEntity<Object>(entities, HttpStatus.OK);
        //add { }
        return jsonString;
    }
}