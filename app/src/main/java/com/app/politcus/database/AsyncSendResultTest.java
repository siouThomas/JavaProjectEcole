package com.app.politcus.database;


import android.location.Location;
import android.os.AsyncTask;
import android.widget.Toast;

import com.app.politcus.App;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Antoine on 19/10/2016.
 */

public class AsyncSendResultTest extends AsyncTask<String, Void, String> {

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... arg0) {

        String resultat = arg0[0];
        String latitude = arg0[1];
        String longitude = arg0[2];
        String uuid = DAO.getInstance().getUUID();


        URL url;


        String data = "UUID=" + uuid + "&RESULTAT=" + resultat + "&LATITUDE=" + latitude + "&LONGITUDE=" + longitude;

        try {
            url = new URL("http://politicus.esy.es/addResultTest.php");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.connect();

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(data);

            writer.flush();
            writer.close();
            os.close();

            conn.getInputStream();



            //}
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            /*} finally {
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }*/
        }

        return " ";
    }

    //@Override
    protected void onPostExecute(String result) {

        Toast msg = Toast.makeText(App.getContext(), "Résultat envoyé avec géolocalisation", Toast.LENGTH_SHORT);
        msg.show();

    }

}