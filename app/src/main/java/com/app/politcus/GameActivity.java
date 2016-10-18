package com.app.politcus;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.app.politcus.game.QuizzFragment;
import com.app.politcus.game.TestFragment;

public class GameActivity extends AppCompatActivity
        implements TestFragment.OnFragmentInteractionListener, QuizzFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
