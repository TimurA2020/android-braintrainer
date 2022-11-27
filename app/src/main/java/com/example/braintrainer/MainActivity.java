package com.example.braintrainer;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    int maxScore;
    private TextView textViewTimer;
    private TextView correctAnswers;
    String question;
    private TextView questionTextView;
    private Button button;
    private Button button2;
    private Button button3;
    private Button button4;
    List<Button> buttons;
    int correctAnswerIndex;
    int correctAnswer;
    int totalNumberOfQuestions;
    int totalNumberOfCorrectAnswers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewTimer = findViewById(R.id.textViewTimer);
        correctAnswers = findViewById(R.id.correctAnswers);
        questionTextView = findViewById(R.id.textView);
        button = findViewById(R.id.button);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);
        buttons = new ArrayList<>();
        buttons.add(button);
        buttons.add(button2);
        buttons.add(button3);
        buttons.add(button4);
        totalNumberOfQuestions = 0;
        totalNumberOfCorrectAnswers = 0;
        startCountdown();
        generateQuestion();
    }

    private void startCountdown(){
        CountDownTimer timer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long l) {
                int seconds = (int) l / 1000;
                seconds++;
                textViewTimer.setText(Integer.toString(seconds));
            }

            @Override
            public void onFinish() {
                Toast.makeText(MainActivity.this, "Countdown completed", Toast.LENGTH_SHORT).show();
                textViewTimer.setText(Integer.toString(0));
                Intent intent = new Intent(MainActivity.this, EndGameActivity.class);
                intent.putExtra("answers", totalNumberOfCorrectAnswers);
                startActivity(intent);
            }
        };
        timer.start();
    }



    public void generateQuestion(){
        Random random = new Random();
        String operators[] = {"+", "-"};
        String randomOperator = operators[random.nextInt(operators.length)];
        int firstNumber = (int) (Math.random() * 100);
        int secondNumber = (int) (Math.random() * 100);
        if(randomOperator.equals("+")) {
            correctAnswer = firstNumber + secondNumber;
            question = "" + firstNumber + "+" + secondNumber;
        }
        else {
            correctAnswer = firstNumber - secondNumber;
            question = "" + firstNumber + "-" + secondNumber;
        }
        questionTextView.setText(question);
        correctAnswerIndex = (int) (Math.random() * buttons.size());
        int tag;
        int[] answers = new int[4];
        for(int i = 0; i < buttons.size(); i++){
            if (i == correctAnswerIndex){
                buttons.get(i).setText(String.valueOf(correctAnswer));
                answers[i] = correctAnswer;
            }else{
                int incorrectAnswer = getRandomWithExclusion(random, correctAnswer - 20, correctAnswer + 20, answers);
                answers[i] = incorrectAnswer;
                buttons.get(i).setText(String.valueOf(incorrectAnswer));
            }
        }
    }

    public void validateAnswer(View view){
        Button button = (Button) view;
        int tag = Integer.parseInt((String) button.getTag());


        Log.i("answers", tag + " " + correctAnswerIndex);
        if(tag == correctAnswerIndex){
            Toast.makeText(this, "Correct", Toast.LENGTH_SHORT).show();
            totalNumberOfCorrectAnswers++;
        }else{
            Toast.makeText(this, "Incorrect", Toast.LENGTH_SHORT).show();
        }
        totalNumberOfQuestions++;

        correctAnswers.setText(String.format(Locale.getDefault(),"%d / %d", totalNumberOfCorrectAnswers, totalNumberOfQuestions));
        generateQuestion();
    }

    public int getRandomWithExclusion(Random rnd, int start, int end, int... exclude) {
        int random = start + rnd.nextInt(end - start + 1 - exclude.length);
        for (int ex : exclude) {
            if (random < ex) {
                break;
            }
            random++;
        }
        return random;
    }


}