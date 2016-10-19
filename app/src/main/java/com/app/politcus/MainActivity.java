package com.app.politcus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Define interractions for the 3 buttons
        Button testButton = (Button) findViewById(R.id.btn_test);
        Button quizzButton = (Button) findViewById(R.id.btn_quizz);
        Button miniGameButton = (Button) findViewById(R.id.btn_mini_game);

        testButton.setOnClickListener(this);
        quizzButton.setOnClickListener(this);
        miniGameButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_test){
            this.onTestButtonClick((Button) v);
        } else if(v.getId() == R.id.btn_quizz){
            this.onQuizzButtonClick((Button) v);
        } else if(v.getId() == R.id.btn_mini_game){
            this.onMiniGameButtonClick((Button) v);
        }
    }

    private void onTestButtonClick(Button btn){
        Intent nextActivity = new Intent(this, GameActivity.class);
        nextActivity.putExtra("FragmentToLoad", "Test");
        startActivity(nextActivity);
    }

    private void onQuizzButtonClick(Button btn){
        Intent nextActivity = new Intent(this, GameActivity.class);
        nextActivity.putExtra("FragmentToLoad", "Quizz");
        startActivity(nextActivity);
    }

    private void onMiniGameButtonClick(Button btn){
        Intent nextActivity = new Intent(this, GameActivity.class);
        nextActivity.putExtra("FragmentToLoad", "MiniGame");
        startActivity(nextActivity);
    }
}