package com.app.politcus;

import android.app.Fragment;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.app.politcus.game.QuizzFragment;
import com.app.politcus.game.test.TestFragment;

public class GameActivity extends AppCompatActivity
        implements TestFragment.OnFragmentInteractionListener, QuizzFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
      //System.out.println("testou1");
        Fragment fragment;
        String fragmentToLoad = getIntent().getStringExtra("FragmentToLoad");
        if(fragmentToLoad.equals("Test")){
            fragment = TestFragment.newInstance();
        } else if(fragmentToLoad.equals("Quizz")){
            fragment = QuizzFragment.newInstance();
        } else {    // MiniGame
            fragment = TestFragment.newInstance();
        }

        getFragmentManager().beginTransaction().add(R.id.activity_quizz, fragment).commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
