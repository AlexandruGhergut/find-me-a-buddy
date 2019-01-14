package com.fmi.findmeabuddy.matching;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

    public double StringtoInt(List<String[]> user1, List<String[]> user2){

        double score = 0;
        List<String> list1 = new ArrayList<String>();
        List<String> list2 = new ArrayList<String>();

        Iterator user1_hobbies = user1.iterator();
        Iterator user2_hobbies = user2.iterator();
        while (user1_hobbies.hasNext() && user2_hobbies.hasNext()) {
            Object[] u1_obj = (Object[]) user1_hobbies.next();
            Object[] u2_obj = (Object[]) user2_hobbies.next();

            for (int i=0;i<=4;i++){ //hardcoded, i know
                list1.add(String.valueOf(u1_obj[i]));
                list2.add(String.valueOf(u2_obj[i]));
            }
        }

        List<String> unique = new ArrayList<String>(list1);
        for (String x : list2){
            if (!unique.contains(x))
                unique.add(x);
        }

        for(String model : unique) {
            System.out.println(unique.size());
            System.out.println(model);
        }

        int[] leftVector = new int[unique.size()];
        int[] rightVector = new int[unique.size()];

        for (String x : unique){
            if(list1.contains(x))
                leftVector[unique.indexOf(x)] = 1;
            if(list2.contains(x))
                rightVector[unique.indexOf(x)] = 1;
        }

        score = cosSim(leftVector,rightVector);
        return score;
    }
}