package com.app.politcus.questions;

/**
 * Created by Thomas on 11/10/2016.
 */

/**
 *
 * Enum permettant de stocker les différents états de réponse possibles
 *
 * L'état est stoker sous forme d'un int
 *
 * Chaque type de réponse possible est à ajouter dans l'enum
 *
 */
public enum Answer {
    None(0),

    False(1),

    True(2),

    //TODO add les autres type exemple partie politique
    Droite(3),

    Gauche(4),

    Libertaire(5),

    Communautariste(6);

    private int value;

    Answer(int value) {
        this.value = value;
    }

    public int get() {
        return value;
    }
}
