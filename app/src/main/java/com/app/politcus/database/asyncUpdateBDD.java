package com.app.politcus.database;

/**
 * Created by Antoine on 18/10/2016.
 */

import android.os.AsyncTask;

import com.app.politcus.questions.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Antoine on 08/09/2016.
 */

public class AsyncUpdateBDD extends AsyncTask<Void, Void, String> {

  private ArrayList<QuestionTest> questionsTest;
  private ArrayList<QuestionQuizz> questionsQuizz;

  public AsyncUpdateBDD(){
    questionsTest = new ArrayList<QuestionTest>();
    questionsQuizz = new ArrayList<QuestionQuizz>();
  }

  @Override
  protected void onPreExecute() {
    super.onPreExecute();
  }

  @Override
  protected String doInBackground(Void... arg0) {

    URL url;
    BufferedReader br = null;
    StringBuilder sb = new StringBuilder();
    String line;

    try {

      url = new URL("http://politicus.esy.es/importQuestions.php");
      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
      conn.setRequestMethod("GET");
      conn.setConnectTimeout(15000);
      conn.setReadTimeout(15000);
      conn.connect();

      InputStream input = new BufferedInputStream(conn.getInputStream());
      br = new BufferedReader(new InputStreamReader(input));

      while ((line = br.readLine()) != null) {
        sb.append(line);
      }

    } catch (MalformedURLException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (br != null) {
        try {
          br.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    String strJson2 = sb.toString();
    return strJson2;



  }

  //@Override
  protected void onPostExecute(String result) {

    try {
      JSONObject jsonRootObject = new JSONObject(result);


      JSONArray jsonArrayTest = jsonRootObject.optJSONArray("resultTest");
      JSONArray jsonArrayQuizz = jsonRootObject.optJSONArray("resultQuizz");

      for (int i = 0; i < jsonArrayTest.length(); i++) {
        JSONObject jsonObject = jsonArrayTest.getJSONObject(i);

        QuestionTest questionTest = new QuestionTest();

        questionTest.setId(Integer.parseInt(jsonObject.optString("ID")));
        questionTest.setTitle(jsonObject.optString("TITLE"));
        String orientationStr = jsonObject.optString("ORIENTATION");
        if (orientationStr.equals("G"))
          questionTest.setOrientation(Orientation.Gauche);
        if (orientationStr.equals("D") )
          questionTest.setOrientation(Orientation.Droite);
        if (orientationStr.equals("C") )
          questionTest.setOrientation(Orientation.Communautariste);
        if (orientationStr.equals("L") )
          questionTest.setOrientation(Orientation.Libertaire);

        questionsTest.add(questionTest);
      }


      for (int i = 0; i < jsonArrayQuizz.length(); i++) {
        JSONObject jsonObject = jsonArrayQuizz.getJSONObject(i);

        QuestionQuizz questionQuizz = new QuestionQuizz();

        questionQuizz.setId(Integer.parseInt(jsonObject.optString("ID")));
        questionQuizz.setTitle(jsonObject.optString("TITLE"));
        if (Integer.parseInt(jsonObject.optString("ANSWER")) == 0 )
          questionQuizz.setAnswer(false);
        else
          questionQuizz.setAnswer(true);

        questionsQuizz.add(questionQuizz);

      }

      DAO dao = DAO.getInstance() ;
      dao.updateDb(questionsTest, questionsQuizz);

    } catch (JSONException e) {
      e.printStackTrace();
    }

  }

}
