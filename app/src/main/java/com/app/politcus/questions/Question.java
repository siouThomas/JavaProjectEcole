package com.app.politcus.questions;

/**
 * Created by Thomas on 11/10/2016.
 */


import java.util.ArrayList;

/**
 * Class qui represente une question
 *
 * Chaque question contient :
 *
 * String title qui est un intitulé de la question
 * QuestionType type qui est le type de la question
 * Answer answer qui est le type de réponse à cette question
 */

public abstract class Question
{


    private int id;
    private String title;

    //final private ArrayList<Answer> answers = new ArrayList<Answer>();


    //TODO faire une class pour les exeptions jetés

    public Question()
    {
        this("");
    }


    public Question(String title)
    {
        this.title = title;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getTitle() {
        return title;

    }

    public void setTitle(String title) {
        this.title = title;
    }





}
