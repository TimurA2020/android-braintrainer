package com.example.braintrainer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.prefs.Preferences;

public class EndGameActivity extends AppCompatActivity {

    int maxScore;
    private TextView maxScoreTextView;
    private TextView yourScoreTextView;
    int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        maxScoreTextView = findViewById(R.id.maxScore);
        yourScoreTextView = findViewById(R.id.yourScore);
        if (preferences != null){
            maxScore = preferences.getInt("maxScore", 0);
        }
        Intent intent = getIntent();
        if(intent != null) {
            Log.i("extra", "has extra");
            score = intent.getIntExtra("answers", 0);
        }
        Log.i("score", score + " ");
        try {
        } catch(Exception e){
            e.printStackTrace();
            }
        if(score > maxScore){
            Toast.makeText(this, "Congratulations New Record!", Toast.LENGTH_SHORT).show();
            maxScore = score;
        }
        preferences.edit().putInt("maxScore", maxScore).apply();
        maxScoreTextView.setText("Max score: " + maxScore);
        yourScoreTextView.setText("Your score: " + score);
    }

    public void tryAgain(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}