package com.app.politcus.game.quizz;

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
import com.app.politcus.questions.QuestionManager;
import com.app.politcus.questions.QuestionTest;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link QuizzFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link QuizzFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuizzFragment extends Fragment{

    private QuestionTest currentQuestion;
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_quizz, container, false);

        DisplayNextRandomQuestion();

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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void DisplayNextRandomQuestion(){
        currentQuestion = QuestionManager.getInstance().getRandomQuestionTest();
        TextView text = (TextView) getView().findViewById(R.id.text_question);

        text.setText(currentQuestion.getTitle());
    }
}
