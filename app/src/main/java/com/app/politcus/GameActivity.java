package com.app.politcus;

import android.app.Fragment;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.app.politcus.game.QuizzFragment;
import com.app.politcus.game.test_coord.TestFragment;
import com.app.politcus.game.test_coord.TestResultFragment;

public class GameActivity extends AppCompatActivity
        implements TestFragment.OnFragmentInteractionListener, QuizzFragment.OnFragmentInteractionListener, TestResultFragment.OnFragmentInteractionListener{

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
        } 

        getFragmentManager().beginTransaction().replace(R.id.activity_game, fragment).commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
