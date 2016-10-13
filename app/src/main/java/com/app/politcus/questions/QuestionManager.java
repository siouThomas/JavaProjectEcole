package com.app.politcus.questions;

import java.util.ArrayList;
import java.util.Random;

public class QuestionManager {

    private static volatile QuestionManager instance;

    final private ArrayList<QuestionQuizz> questionsQuizz = new ArrayList<QuestionQuizz>();
    final private ArrayList<QuestionTest> questionsTest = new ArrayList<QuestionTest>();

    // List the questions already requested. Use this to avoid sending the same question too many
    // times to the user.
    final private ArrayList<Integer> questionsTestServed = new ArrayList<Integer>();
    final private ArrayList<Integer> questionsQuizzServed = new ArrayList<Integer>();


    private QuestionManager() {
    }

    /**
     * permet de récuperer l'instance du singleton
     *
     * @return
     */
    public final static QuestionManager getInstance() {
        if (instance == null) {
            synchronized (QuestionManager.class) {
                if (instance == null) {
                    instance = new QuestionManager();
                }
            }
        }
        return instance;
    }

    /**
     * en attendant ANTOINE LIEBERTTTla liason bdd en entre en dur les question
     */
    void init() {
        QuestionQuizz tmp = new QuestionQuizz(true);
        QuestionTest tmp2 = new QuestionTest();

        for (int i = 0; i < 5; i++) {
            tmp.setTitle("Question example" + i);
            tmp.setId(i);
            this.questionsQuizz.add(tmp);

            tmp2.setTitle("Question test example " + i);
            tmp2.setId(i);
            questionsTest.add(tmp2);
        }
        questionsTestServed.clear();
        questionsQuizzServed.clear();
    }

    public QuestionTest getRandomQuestionTest(){
        // reset counter if every questions were already used once.
        if(questionsTestServed.size() == questionsTest.size()){
            questionsTestServed.clear();
        }

        Random rand = new Random();
        int id = -1;

        do{
            id = rand.nextInt(questionsTest.size());
        } while (questionsTestServed.contains(id));

        return getQuestionTestWithId(id);
    }

    public QuestionTest getQuestionTestWithId(int id){

        for(QuestionTest q : questionsTest){
            if(q.getId() == id){
                questionsTestServed.add(id);
                return q;
            }
        }

        if(questionsTest.isEmpty()){
            QuestionTest q = new QuestionTest();
            q.setTitle("Error : Empty database. Please complain to Mr Liebert");
            return q;
        }
        return questionsTest.get(0);
    }

    public QuestionQuizz getQuestionQuizz(){
        // reset counter if every questions were already used once.
        if(questionsQuizzServed.size() == questionsQuizz.size()){
            questionsQuizzServed.clear();
        }

        Random rand = new Random();
        int id = -1;

        do{
            id = rand.nextInt(questionsTest.size());
        } while (questionsTestServed.contains(id));

        return getQuestionQuizzWithId(id);
    }

    public QuestionQuizz getQuestionQuizzWithId(int id) {

        for (QuestionQuizz q : questionsQuizz) {
            if (q.getId() == id) {
                questionsTestServed.add(id);
                return q;
            }
        }

        if (questionsQuizz.isEmpty()) {
            QuestionQuizz q = new QuestionQuizz();
            q.setTitle("Error : Empty database. Please complain to Mr Liebert");
            return q;
        }
        return questionsQuizz.get(0);
    }
}


/* Cache manamgent should go to the DAO

    void add(Question newQuestions)
    {
            questions.add(newQuestions);
            questionsStocks.add(newQuestions);
    }


    void adds(ArrayList<Question> newQuestions)
    {
        for (Question q : newQuestions) {

            add(q);

        }
    }


    private void initStock()
    {
        for (Question q : questions) {

            questionsStocks.add(q);

        }
    }

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



}

*/
