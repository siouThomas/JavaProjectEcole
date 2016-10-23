package com.app.politcus.game.test;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.politcus.R;
import com.app.politcus.questions.QuestionManager;


public class TestResultFragment extends Fragment implements View.OnClickListener {

    private OnFragmentInteractionListener mListener;
    private float hScoreFinal;
    private float vScoreFinal;

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

        hScoreFinal = QuestionManager.getInstance().getLastHorizontalResultTest();
        vScoreFinal = QuestionManager.getInstance().getLastVerticalResultTest();

        String resultText = " Résultat :\n";

        if (hScoreFinal < 0.5)
            resultText += "Gaucho ";
        else
            resultText += "Droitard ";

        if (vScoreFinal<0.5)
            resultText+= "Communautariste";
        else
            resultText += "Libertaire";

        TextView tv_res = (TextView) view.findViewById(R.id.text_result);
        tv_res.setText(resultText);

        addResultCircle(view);

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
        if(view.getId() == R.id.btn_back){
            // TODO : Find a way to go to main menu
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void addResultCircle(View view){
        BitmapFactory.Options myOptions = new BitmapFactory.Options();
        myOptions.inDither = true;
        myOptions.inScaled = false;
        myOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.grid,myOptions);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);

        Bitmap workingBitmap = Bitmap.createBitmap(bitmap);
        Bitmap mutableBitmap = workingBitmap.copy(Bitmap.Config.ARGB_8888, true);

        float height = bitmap.getHeight();
        float width = bitmap.getWidth();

        double widthPosit = width*hScoreFinal ;
        double heightPosit = height*vScoreFinal ;

        Canvas canvas = new Canvas(mutableBitmap);
        canvas.drawCircle((float)widthPosit , (float)heightPosit, 15, paint);

        ImageView imageView = (ImageView) view.findViewById(R.id.grid);
        imageView.setAdjustViewBounds(true);
        imageView.setImageBitmap(mutableBitmap);
    }
}
