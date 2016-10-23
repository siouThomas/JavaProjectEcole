package com.app.politcus;

import android.*;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.politcus.database.DAO;
import com.app.politcus.database.LocationManagement;
import com.app.politcus.questions.QuestionManager;
import com.app.politcus.questions.QuestionQuizz;
import com.app.politcus.questions.QuestionTest;

import java.util.ArrayList;

public class AntoMainActivity extends AppCompatActivity {


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_anto_main);

    /*
    DAO dao = DAO.getInstance();

    ArrayList<QuestionTest> questionsTest = dao.getAllQuestionTest();
    ArrayList<QuestionQuizz> questionsQuizz = dao.getAllQuestionQuizz();

    int nbQuestionsTest = questionsTest.size();
    int nbQuestionsQuizz = questionsQuizz.size();

    TextView tv1 = (TextView) findViewById(R.id.tv1);
    tv1.setText(Integer.toString(nbQuestionsTest));

    TextView tv2 = (TextView) findViewById(R.id.tv2);
    tv2.setText(Integer.toString(nbQuestionsQuizz));

    dao.launchAsync();

    TextView tv3 = (TextView) findViewById(R.id.tv3);
    tv3.setText(Integer.toString(nbQuestionsTest));

    TextView tv4 = (TextView) findViewById(R.id.tv4);
    tv4.setText(Integer.toString(nbQuestionsQuizz));


    LocationManagement locationManagement = new LocationManagement();*/

    Button backButton = (Button) findViewById(R.id.btn_back);

    backButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

      }
    });



    //float hScoreFinal = QuestionManager.getInstance().getLastHorizontalResultTest();
    //float vScoreFinal = QuestionManager.getInstance().getLastVerticalResultTest();

    double hScoreFinal = 0.45;
    double vScoreFinal = 0.75;




  }


}
