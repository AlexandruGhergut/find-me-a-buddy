package com.fmi.findmeabuddy.matching;

import com.fmi.findmeabuddy.domain.Account;
import com.fmi.findmeabuddy.domain.Hobby;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class UserMatch {

    private double cosSim(int[] leftVector, int[] rightVector) {
        if (leftVector.length != rightVector.length) {return -1;}
        double dotProduct = 0;
        double leftNorm = 0;
        double rightNorm = 0;
        for (int i = 0; i < leftVector.length; i++) {
            dotProduct += leftVector[i] * rightVector[i];
            leftNorm += leftVector[i] * leftVector[i];
            rightNorm += rightVector[i] * rightVector[i];
        }

        double result = dotProduct / (Math.sqrt(leftNorm) * Math.sqrt(rightNorm));
        return result;
    }

    public double StringtoInt(Account user1, Account user2){

        double score = 0;

        Set<Hobby> user1Hobbies = user1.getProfile().getHobbies();
        Set<Hobby> user2Hobbies = user2.getProfile().getHobbies();

        List<Hobby> unique = new ArrayList<>(user1Hobbies);
        for (Hobby hobby : user2Hobbies){
            if (!unique.contains(hobby))
                unique.add(hobby);
        }

        for(Hobby model : unique) {
            System.out.println(unique.size());
            System.out.println(model);
        }

        int[] leftVector = new int[unique.size()];
        int[] rightVector = new int[unique.size()];

        for (Hobby x : unique){
            if(user1Hobbies.contains(x))
                leftVector[unique.indexOf(x)] = 1;
            if(user2Hobbies.contains(x))
                rightVector[unique.indexOf(x)] = 1;
        }

        score = cosSim(leftVector,rightVector);
        return score;
    }
}
