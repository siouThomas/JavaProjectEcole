package com.app.politcus;

import android.app.Fragment;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.app.politcus.game.quizz.QuizzFragment;
import com.app.politcus.game.TestFragment;

public class GameActivity extends AppCompatActivity
        implements TestFragment.OnFragmentInteractionListener, QuizzFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Fragment fragment;
        String fragmentToLoad = getIntent().getStringExtra("FragmentToLoad");
        if(fragmentToLoad == "Test"){
            fragment = TestFragment.newInstance("1", "2");
        } else if(fragmentToLoad == "Quizz"){
            fragment = QuizzFragment.newInstance();
        } else {    // MiniGame
            fragment = TestFragment.newInstance("1", "2");
        }

        getFragmentManager().beginTransaction().add(R.id.activity_quizz, fragment).commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
