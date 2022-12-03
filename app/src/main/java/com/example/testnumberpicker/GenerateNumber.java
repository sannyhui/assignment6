package com.example.testnumberpicker;

//Separate class base on SRP.
public class GenerateNumber {

    //Method to generate secret number and retrun string.
    public static String genSecretNum() {

        //Random string from 0 to 9999.
        String random = String.valueOf((int) (Math.random()*10000));

        //Add "0" to fill the blank space of the string if length is under 4.
        for (int i =  random.length(); i < 4; i ++) {
            random = "0" + random;
        }

        //Return secret number
        return random;
    }
}
