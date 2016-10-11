package com.app.politcus.com.politicus.questions;

import java.util.ArrayList;

/**
 * Created by Thomas on 11/10/2016.
 */


public class QuestionManager
{

    private static volatile QuestionManager instance;

    final private ArrayList<Question> questions = new ArrayList<Question>();

    private QuestionManager()
    {
    }

    /**
     * en attendant la liason bdd en entre en dur les question
     */
    void init()
    {
        Question tmp = new Question();

        for(int i = 0; i < 5 ; i ++)
        {
            tmp.setTitle("Question example"+ i );
            tmp.setType(QuestionType.TrueFalse);

            try
            {
                tmp.addAnswers(Answer.Vrais);

            } catch (Exception e)
            {
                //Todo gerer les erreurs
                System.out.println("error dans les question");
                e.printStackTrace();
            }


            this.questions.add(tmp);
        }
    }

    /**
     * permet de récuperer l'instance du singleton
     * @return
     */
    public final static QuestionManager getInstance()
    {
        if (instance == null)
        {
            synchronized(QuestionManager.class)
            {
                if (instance == null)
                {
                    instance = new QuestionManager();
                    instance.init();
                }
            }
        }
        return instance;
    }

    public ArrayList<Question> get(int numberQuestion) throws Exception
    {

        if(this.questions.size() < numberQuestion)
            throw new Exception();

        //Todo refactor choix question ....
        ArrayList<Question> questionsTmp = new ArrayList<Question>();

        for ( int i = 0; i < numberQuestion; i ++ ) {

            questionsTmp.add(this.questions.get(i));
        }

        return questionsTmp;

    }
}
