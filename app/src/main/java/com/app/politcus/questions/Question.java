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

public class Question
{


    private int id;
    private String title;
    private QuestionType type;
    //TODO reflechir sur une possibilité de multi réponse a voir pour le moment simple constructor one answer
    final private ArrayList<Answer> answers = new ArrayList<Answer>();
    final private ArrayList<Answer> choices = new ArrayList<Answer>();

    //TODO faire une class pour les exeptions jetés

    public Question()
    {
        this("", QuestionType.None, Answer.None);
    }


    public Question(String title)
    {
        this(title, QuestionType.None, Answer.None);
    }

    public Question(String title, QuestionType type, Answer answer)
    {
        this.title = title;
        this.type = type;

        this.answers.add(answer);
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

    public QuestionType getType() {
        return type;
    }

    public void setType(QuestionType type) {
        this.type = type;
    }


    public ArrayList<Answer> getAnswers() {
        return answers;
    }


    public void addAnswers(Answer answer) throws Exception {

        if(this.answers.contains(answer))
            throw new Exception();

        this.answers.add(answer);

    }

    public void deleteAnswers(Answer answer) throws Exception {
        if(!this.answers.contains(answer))
            throw new Exception();

        this.answers.remove(answer);
    }



}
