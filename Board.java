package org.example;

public class Board {
    private Card[][] grid;
    private State numberOfPlayer;

    public Board (int numOfPlayers){
        if (numOfPlayers) {
            numberOfPlayer = new TwoPlayer()
        }

    } //raandomicamente riempie la matrice con delle carte
    public Card get(Coordinates c){}
    public boolean valid(Coordinates c) {} // controlla che la tessere abbia un lato libero
    public void remove(Coordinates c) {}
    public boolean control(){} // controlla se ci sono solo pezzi isolati
    public void refresh (){
        numberOfPlayer.refresh();
    } // inserisce nuove tessere nella bord

}
