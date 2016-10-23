package com.app.politcus;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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

        testButton.setOnClickListener(this);
        quizzButton.setOnClickListener(this);


        if (ContextCompat.checkSelfPermission(App.getContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    111);
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_test){
            this.onTestButtonClick((Button) v);
        } else if(v.getId() == R.id.btn_quizz){
            this.onQuizzButtonClick((Button) v);
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