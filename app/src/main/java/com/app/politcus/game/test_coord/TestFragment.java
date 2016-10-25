package com.app.politcus.game.test_coord;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.app.politcus.App;
import com.app.politcus.R;
import com.app.politcus.database.SendResultManager;
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
    private int scoreGauche = 0;
    private int scoreDroite = 0;
    private int scoreLibertaire = 0;
    private int scoreCommunautariste = 0;
    private QuestionTest currentQuestion;
    private int currentProgress = 1;
    private int nbQuestionsTest;

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

        nbQuestionsTest = QuestionManager.getInstance().getQuestionsTestNumber();
        currentQuestion = QuestionManager.getInstance().getQuestionTestWithId(currentProgress);
        TextView progress = (TextView) view.findViewById(R.id.text_progress);
        TextView text = (TextView) view.findViewById(R.id.text_question);
        progress.setText("Question " + Integer.toString(currentProgress) + " sur " + Integer.toString(nbQuestionsTest));
        text.setText(currentQuestion.getTitle());

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
            if (currentProgress<nbQuestionsTest)
                displayNextQuestion();
            else
                showResults();
        }
    }


    private void displayNextQuestion() {
        currentProgress++;
        currentQuestion = QuestionManager.getInstance().getQuestionTestWithId(currentProgress);
        TextView progress = (TextView) getView().findViewById(R.id.text_progress);
        TextView text = (TextView) getView().findViewById(R.id.text_question);
        Button nextButton = (Button) getView().findViewById(R.id.btn_next);

        progress.setText("Question " + Integer.toString(currentProgress) + " sur " + Integer.toString(nbQuestionsTest));
        text.setText(currentQuestion.getTitle());
        if (currentProgress == nbQuestionsTest)
            nextButton.setText("Terminer");
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void processScore() {
        SeekBar bar = (SeekBar) getView().findViewById(R.id.progress_bar);
        Orientation orientation = currentQuestion.getOrientation();


        int choix = bar.getProgress()-2;
        switch (orientation) {
            case Droite:
                //scoreDroite += choix;
                hScore += choix;
                break;
            case Gauche:
                //scoreGauche += choix;
                hScore -= choix;
                break;
            case Communautariste:
                //scoreCommunautariste += choix;
                vScore -= choix;
                break;
            case Libertaire:
                //scoreLibertaire+=choix;
                vScore += choix;
                break;
        }
    }

    private void showResults() {

        int nbGauche = QuestionManager.getInstance().getQuestionsTestGaucheNumber();
        int nbDroite = QuestionManager.getInstance().getQuestionsTestDroiteNumber();
        int nbCommunautariste = QuestionManager.getInstance().getQuestionsTestCommunautaristeNumber();
        int nbLibertaire = QuestionManager.getInstance().getQuestionsTestLibertaireNumber();

        int hScoreMax = 2*nbGauche + 2*nbDroite;
        int vScoreMax = 2*nbLibertaire +2*nbCommunautariste;

        float hScoreFinal = ((float) hScore + hScoreMax) / ((float) 2 * hScoreMax);
        float vScoreFinal = ((float) vScore + vScoreMax) / ((float) 2 * vScoreMax);


        QuestionManager.getInstance().insertResultsTest(hScoreFinal,vScoreFinal);

        String result = "";
        if (hScoreFinal<0.5)
            result = "G";
        else
            result = "D";

        if (vScoreFinal<0.5)
            result += "C";
        else
            result += "L";


        if (ContextCompat.checkSelfPermission(App.getContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            SendResultManager sendResultManager = new SendResultManager(result);
        }

        // TODO : lancer le fragment TestResultFragment
    }

    public void setProgressBarColor()
    {
        SeekBar bar = (SeekBar) getView().findViewById(R.id.progress_bar);
        int progress= bar.getProgress();


        if (progress<2)
        {
            bar.setProgressDrawable(getView().getResources().getDrawable(R.drawable.progressbar_red));
            bar.setSecondaryProgress(0);
        }
        else
        {
            bar.setProgressDrawable(getView().getResources().getDrawable(R.drawable.progressbar_green));
            bar.setSecondaryProgress(2);
        }
    }

    public void onResume()
    {
        super.onResume();
        SeekBar bar = (SeekBar) getView().findViewById(R.id.progress_bar);
        bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                setProgressBarColor();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
