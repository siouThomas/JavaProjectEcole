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

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TestFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TestFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TestFragment extends Fragment implements View.OnClickListener {

    private OnFragmentInteractionListener mListener;
    private int hScore = 0;
    private int vScore = 0;
    private int hScorePlusMax = 0;
    private int hScoreMinusMax = 0;
    private int vScorePlusMax = 0;
    private int vScoreMinusMax = 0;
    private QuestionTest currentQuestion;
    private int currentProgress = 0;

    public TestFragment() {
        // Required empty public constructor
    }

    public static TestFragment newInstance() {
        TestFragment fragment = new TestFragment();
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
        View view = inflater.inflate(R.layout.fragment_test, container, false);
        Button nextButton = (Button) view.findViewById(R.id.btn_next);
        nextButton.setOnClickListener(this);
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
