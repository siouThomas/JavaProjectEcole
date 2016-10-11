package com.app.politcus.questions;

/**
 * Created by Thomas on 11/10/2016.
 */


/**
 *
 * Enum permettant de stocker les différents états possible des question
 *
 * L'état est stoker sous forme d'un int
 *
 * Chaque type de question possible est à ajouter dans l'enum
 *
 */
public enum QuestionType {
    None(0),

    TrueFalse(1),

    OneAnswer(2),

    MultiAnswer(3);

    private int value;

    QuestionType(int value) {
        this.value = value;
    }

    public int get() {
        return value;
    }
}
