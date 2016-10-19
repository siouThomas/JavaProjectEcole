package com.app.politcus.game;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.app.politcus.R;
import com.app.politcus.questions.QuestionManager;
import com.app.politcus.questions.QuestionQuizz;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link QuizzFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link QuizzFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuizzFragment extends Fragment implements View.OnClickListener {

    private QuestionQuizz currentQuestion;
    private int score;
    private boolean gameOver;
    private OnFragmentInteractionListener mListener;

    public QuizzFragment() {
        // Required empty public constructor
    }

    public static QuizzFragment newInstance() {
        QuizzFragment fragment = new QuizzFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        score = 0;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_quizz, container, false);

        currentQuestion = QuestionManager.getInstance().getRandomQuestionQuizz();
        TextView text = (TextView) view.findViewById(R.id.text_question);
        text.setText(currentQuestion.getTitle());

        Button trueButton = (Button) view.findViewById(R.id.btn_true);
        Button falseButton = (Button) view.findViewById(R.id.btn_false);
        trueButton.setOnClickListener(this);
        falseButton.setOnClickListener(this);

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
        if(view.getId() == R.id.btn_true){
            if(gameOver){
                RestartGame();
            } else if(currentQuestion.getAnswer()){    // Correct Answer
                score++;
                displayNextRandomQuestion();
            } else {
                displayGameOverMessage();
            }
        } else if(view.getId() == R.id.btn_false) {
            if(gameOver) {
                Log.i("Log", "You are not allowed to stop playing");
            } else if (currentQuestion.getAnswer()) { // Incorrect Answer
                displayGameOverMessage();
            } else {
                displayNextRandomQuestion();
            }
        }
    }

    private void displayGameOverMessage() {
        TextView text = (TextView) getView().findViewById(R.id.text_question);
        text.setText("Partie terminée ! \n Score : " + Integer.toString(score));

        Button trueButton = (Button) getView().findViewById(R.id.btn_true);
        Button falseButton = (Button) getView().findViewById(R.id.btn_false);

        trueButton.setText("Recommencer");
        falseButton.setText("Quitter");
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void displayNextRandomQuestion(){
        currentQuestion = QuestionManager.getInstance().getRandomQuestionQuizz();
        TextView text = (TextView) getView().findViewById(R.id.text_question);

        text.setText(currentQuestion.getTitle());
    }

    public void RestartGame(){
        Button trueButton = (Button) getView().findViewById(R.id.btn_true);
        Button falseButton = (Button) getView().findViewById(R.id.btn_false);

        trueButton.setText("Vrai");
        falseButton.setText("Faux");
        displayNextRandomQuestion();
    }
}
