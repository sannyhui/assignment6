package com.example.testnumberpicker;

//Separate class base on SRP.
public class Checking  {

    //Declare variable
    char secretNumber[];
    char guessNumber[];

    //Constructor to get two character arrays and pass to class variables.
    public Checking(char secretNumber[], char guessNumber[]) {
        this.secretNumber = secretNumber;
        this.guessNumber = guessNumber;
    }

    //Method to determine quantity of match number and position.
    public int checkGuess() {

        //Declare variable correctAnswer
        int correctAnswer = 0;

        //Compare both arrays.
        for (int i = 0; i <4; i ++) {
            if (secretNumber[i] == guessNumber[i]) {
                //+1 if match is found.
                correctAnswer ++;
            }
        }
        //Return variable.
        return correctAnswer;
    }

    //Method to determine position of matched number and return string.
    public char[] checkPosition() {

        //Declare string.
        char correctPosition[] = new char[4];

        //Compare both arrays.
        for (int i = 0; i <4; i ++) {

            //Add number or ? to the character array.
            if (secretNumber[i] == guessNumber[i]) {
                correctPosition[i] = secretNumber[i];
            } else {
                correctPosition[i] = '?';
            }
        }

        //Return string.
        return correctPosition;
    }

    //Method to determine how many matched number even though position is incorrect.
    public int checkNumber() {

        //Declare number
        int correctNumber = 0;

        //Initialize the array to identify whether the character is used or not.
        boolean postionChecking[] = {false, false, false, false};

        //Compare secret number with guess number one by one, but skip the used character.
        for (int i = 0; i <4; i ++) {
            for (int j = 0; j < 4; j ++) {

                //If number is found and the character in guess number hasn't used.
                if (secretNumber[i] == guessNumber[j] && postionChecking[j] == false) {

                    //Flag the array in that position.
                    postionChecking[j] = true;

                    //Count number of matched number and break the j loop.
                    correctNumber ++;
                    break;
                }
            }
        }

        //return number
        return correctNumber;
    }

}