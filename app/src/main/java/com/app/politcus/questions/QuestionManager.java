package com.app.politcus.questions;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Thomas on 11/10/2016.
 */


public class QuestionManager
{

    private static volatile QuestionManager instance;

    final private ArrayList<Question> questions = new ArrayList<Question>();

    final private ArrayList<Question> questionsStocks = new ArrayList<Question>();

    private QuestionManager()
    {
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
                }
            }
        }
        return instance;
    }

    /**
     * en attendant ANTOINE LIEBERTTTla liason bdd en entre en dur les question
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
                tmp.addAnswers(Answer.Vrai);

            } catch (Exception e)
            {
                //Todo gerer les erreurs
                System.out.println("error dans les question");
                e.printStackTrace();
            }


            this.questions.add(tmp);
        }

        questionsStocks.clear();
    }

    /**
     * add question in storage question and in stock question
     * @param newQuestions
     */
    void add(Question newQuestions)
    {
            questions.add(newQuestions);
            questionsStocks.add(newQuestions);
    }


    /**
     * add some question
     *
     * @param newQuestions
     */
    void adds(ArrayList<Question> newQuestions)
    {
        for (Question q : newQuestions) {

            add(q);

        }
    }

    /**
     * initialisation Question Stocks
     */
    private void initStock()
    {
        for (Question q : questions) {

            questionsStocks.add(q);

        }
    }

    /**
     * get Question in stock. If there are no questions in stock the systeme add the same questions.
     *
     * @return Question
     */
    private Question getQuestionStock()
    {
        if(questionsStocks.size() <= 0) {
            initStock();
        }


        Random random = new Random();
        int nbRandom = random.nextInt(questionsStocks.size());

        Question questionTmp = questionsStocks.get(nbRandom);
        questionsStocks.remove(questionTmp);

        return  questionTmp;
    }

    /**
     *
     * Get several Questions in stocks.
     *
     * @param numberQuestion
     * @return
     * @throws Exception
     */
    public ArrayList<Question> get(int numberQuestion) throws Exception
    {

        if(this.questions.size() < numberQuestion)
            throw new Exception();

        ArrayList<Question> questionsTmp = new ArrayList<Question>();

        for ( int i = 0; i < numberQuestion; i ++ ) {


            questionsTmp.add(getQuestionStock());
        }

        return questionsTmp;

    }
}
