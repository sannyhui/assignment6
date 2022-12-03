package com.example.testnumberpicker;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.example.testnumberpicker.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    //Enable view binding
    private ActivityMainBinding binding;

    //Declare class variables
    int count = 0;     //Number of turns.
    int tips = 0;   //Number of cards which match strings but in wrong position.
    int maxTips = 3;    //Number of tips.
    boolean tipsChecking = false;   //Flag true if tips is used to prevent repeating.
    int correctAnswer =0;  //Number of correct answer.
    char easyTips[] = new char [4]; //Tips for easy mode.
    boolean difficultLevel = true;  //Flag false is easy mode. Default is difficult.

    //Display secret number
    private void resultDisplay (TextView textView[], char content[]) {
        for (int i = 0; i < 4; i ++) {
            textView[i].setText(String.valueOf(content[i]));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        //Define number picker array
        NumberPicker numberPicker[] = {binding.numberPicker1, binding.numberPicker2,
                binding.numberPicker3, binding.numberPicker4};
        //Define text view array.
        TextView viewNumber[] = {binding.secretNumber1, binding.secretNumber2,
                binding.secretNumber3, binding.secretNumber4};

        //Setup number picker ranges.
        for (int i = 0; i < 4; i ++) {
            numberPicker[i].setMinValue(0);
            numberPicker[i].setMaxValue(9);
        }

        //Instance GenerateNumber.
        GenerateNumber generateNumber = new GenerateNumber();
        String secretNumber = generateNumber.genSecretNum();

        //Generate secret number before game start.
        char secretNumberChar[] = secretNumber.toCharArray();

        //Switch on click handling.
        binding.switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    difficultLevel = false; //False is easy.
                } else {
                    difficultLevel = true;  //True is hard.
                }
            }
        });

        //Give up button on click handling.
        binding.giveup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Show secret numbers and related messages
                resultDisplay(viewNumber, secretNumberChar);
                binding.resultString.setText("You gave up the game");
                binding.tipsText.setText("Game has reset.");

                //Generate new set of secret number
                String generateSecretNumber = generateNumber.genSecretNum();
                for (int i = 0; i <4; i++) {
                    secretNumberChar[i] = generateSecretNumber.charAt(i);
                }

                //Reset counters and tips.
                count = 0;
                maxTips = 3;
            }
        });

        //Tips image button on click handling.
        binding.tipsIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Not do anything if it is round 0 or tips flag is triggered.
                if (count != 0  && tipsChecking == false) {
                    //Only show tips when there are tips left.
                    if (maxTips > 0) {
                        tipsChecking = true;
                        maxTips--;
                        binding.tipsText.setText((tips - correctAnswer) +
                                " numbers are in wrong position.");
                    } else {
                        binding.tipsText.setText("Hints has been used up.");
                    }
                }
            }
        });

        //Guess button on click handling.
        binding.resultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Open for user to click tips icon.
                tipsChecking = false;

                //Update number of tips textview.
                binding.tipsText.setText("Hints : " + maxTips + " times left");

                //Show ???? in first round.
                if (count == 0) {
                    char setToOriginal[] = {'?', '?', '?', '?'};
                    resultDisplay(viewNumber, setToOriginal);
                }

                //Pass values of number pickers to array.
                char guessNumberChar[] = new char [4];
                for (int i = 0; i < 4; i ++) {
                    guessNumberChar[i] = Integer.toString(numberPicker[i].getValue()).charAt(0);
                }

                //Instance Checking with both arrays.
                Checking checking = new Checking(secretNumberChar, guessNumberChar);

                //Turn ++ and update textView.
                count ++;
                binding.turnNumber.setText(String.valueOf(count));

                //Check result
                correctAnswer = checking.checkGuess();
                //If result is not 4
                if (correctAnswer != 4) {
                    //Run method to get tips without display it.
                    tips = checking.checkNumber();

                    //Display special message when no match.
                    if (correctAnswer == 0) {
                        binding.resultString.setText("No match found!");
                    } else {
                        //Rest tips for easy level.
                        for (int i = 0; i < 4; i++) {
                            easyTips[i] = '?';
                        }
                        //Run method if level is easy.
                        if (difficultLevel == false) {
                            easyTips = checking.checkPosition();
                        }

                        //If match, revert number, otherwise display ?
                        resultDisplay(viewNumber, easyTips);

                        //Display result in textView
                        binding.resultString.setText(correctAnswer + " numbers are correct");
                    }
                } else {

                    //Show secret numbers and show congratulation messages.
                    resultDisplay(viewNumber, secretNumberChar);
                    binding.resultString.setText("Congratulation, Bingo!");
                    binding.tipsText.setText("Game has reset.");

                    //Generate secret number and rest counters.
                    String generateSecretNumber = generateNumber.genSecretNum();
                    for (int i = 0; i <4; i++) {
                        secretNumberChar[i] = generateSecretNumber.charAt(i);
                    }
                    count = 0;
                    maxTips = 3;
                }
            }
        });
    }
}