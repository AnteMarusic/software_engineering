package org.example;

public class Card {
    public enum Colour {
        BLUE,
        ROSE,
        GREEN,
        LIGHTBLUE,
        YELLOW,
        PALEYELLOW
    }
    private Colour colour ;
    public Card (Colour c){
       this.colour = c;
    } // crea una carta e gli assegna un colore random
    public Colour getColour () {
        return this.colour;
    }

    public void setColour (Colour c) {
        this.colour = c;
    }
}
