package com.app.politcus.database;

import java.util.List;
import java.util.Random;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import com.app.politcus.R;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class TestDatabaseActivity extends ListActivity  {

  private QuizzDAO quizzDAO;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    quizzDAO = new QuizzDAO(this);
    quizzDAO.open();

    List<QuestionQuizz> values = quizzDAO.getAllQuestionQuizz();

    // utilisez SimpleCursorAdapter pour afficher les
    // éléments dans une ListView
    ArrayAdapter<QuestionQuizz> adapter = new ArrayAdapter<QuestionQuizz>(this,
      android.R.layout.simple_list_item_1, values);
    setListAdapter(adapter);
  }

  // Sera appelée par l'attribut onClick
  // des boutons déclarés dans main.xml
  public void onClick(View view) {
    @SuppressWarnings("unchecked")
    ArrayAdapter<QuestionQuizz> adapter = (ArrayAdapter<QuestionQuizz>) getListAdapter();
    QuestionQuizz questionQuizz = null;
    switch (view.getId()) {
      case R.id.add:
        String[] questions = new String[] { "Question 1", "Question 2", "Question 3" };
        int[] answers = new int[] {1,0,1};
        int nextInt = new Random().nextInt(3);
        // enregistrer le nouveau commentaire dans la base de données
        questionQuizz = quizzDAO.createQuestionQuizz(questions[nextInt],answers[nextInt]);
        adapter.add(questionQuizz);
        break;
      case R.id.delete:
        if (getListAdapter().getCount() > 0) {
          questionQuizz = (QuestionQuizz) getListAdapter().getItem(0);
          quizzDAO.deleteQuestionQuizz(questionQuizz);
          adapter.remove(questionQuizz);
        }
        break;
    }
    adapter.notifyDataSetChanged();
  }

  @Override
  protected void onResume() {
    quizzDAO.open();
    super.onResume();
  }

  @Override
  protected void onPause() {
    quizzDAO.close();
    super.onPause();
  }
}

