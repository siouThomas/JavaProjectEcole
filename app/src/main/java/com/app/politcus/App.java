package com.app.politcus;

import android.app.Application;
import android.content.Context;

import com.app.politcus.database.DAO;

public class App extends Application {
    private static Context context;

  public App(){ }
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

  public static Context getContext (){
    return context;
  }
}
