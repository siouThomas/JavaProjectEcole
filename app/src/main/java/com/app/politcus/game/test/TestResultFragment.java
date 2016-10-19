package com.app.politcus.game.test;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.app.politcus.R;
import com.app.politcus.questions.Orientation;
import com.app.politcus.questions.QuestionManager;
import com.app.politcus.questions.QuestionTest;

public class TestResultFragment extends Fragment{

    private OnFragmentInteractionListener mListener;
    private float horizontal;
    private float vertical;

    public TestResultFragment() {
        // Required empty public constructor
    }

    public static TestResultFragment newInstance() {
        TestResultFragment fragment = new TestResultFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_test_result, container, false);
        Button backButton = (Button) view.findViewById(R.id.btn_back);
        backButton.setOnClickListener(this);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btn_next){
            processScore();
            displayNextQuestion();
        }
    }

    private void displayNextQuestion() {
        currentQuestion = QuestionManager.getInstance().getQuestionTestWithId(currentProgress);
        TextView progress = (TextView) getView().findViewById(R.id.text_progress);
        TextView text = (TextView) getView().findViewById(R.id.text_question);

        progress.setText("Question " + Integer.toString(currentProgress) + " sur 36");
        text.setText(currentQuestion.getTitle());
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void processScore() {
        Orientation orientation = currentQuestion.getOrientation();
        float modifier = 0;
        switch (orientation) {
            case Droite:
                hScorePlusMax++;
                hScore += modifier;
                break;
            case Gauche:
                hScoreMinusMax++;
                hScore -= modifier;
                break;
            case Communautariste:
                vScorePlusMax++;
                vScore += modifier;
                break;
            case Libertaire:
                vScoreMinusMax++;
                vScore -= modifier;
                break;
        }
    }
}
