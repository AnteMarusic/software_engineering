package org.example;

import java.util.HashMap;

abstract class Board {
    protected int maxCards;
    protected BagOfCards bag;
    protected Card[] board;
    protected HashMap<Coordinates, Integer> grid;
    private final Player firstPlayer ;

    //board constructor
    public Board(Player player, BagOfCards bag){
        this.firstPlayer = player;
        this.bag = bag;
    }

    //getFirstPlayer method, same for every subclass
    public Player getFirstPlayer(){
        return this.firstPlayer;
    }

    public int getMaxcards (){
        return this.maxCards;
    }

    //fills board with new card taken from the bag
    protected void fill(BagOfCards bag){
        for(int i=0 ; i<getMaxcards() ; i++){
            if(board[i]==null){
                board[i] = bag.collectCard();
            }
        }
    }

    //return card in position Coordinate coor
    private Card getCardAtCoordinate(Coordinates coor){
        return board[grid.get(coor)];
    }

    //remove a card from the board and set the reference to null
    private void removeCardAtCoordinate(Coordinates coor){
        board[grid.get(coor)] = null;
    }

    //probably unuseful, we'll check during class Game implementation
    public void boardProcedure(){

    }
}

class twoPlayersBoard extends Board {
    //2 players constructor
    public twoPlayersBoard(Player player, BagOfCards bag) {
        //call super constructor
        super(player, bag);
        maxCards = 29;
        board = new Card[29];
        //initialize hash map based on num of players
        int start;
        int lenght;
        int value=0;
        this.grid = new HashMap<Coordinates, Integer>();
        for (int i = 1; i < 9; i++) {
            switch (i) {
                case 1 -> {
                    start = 4;
                    lenght = 2;
                }
                case 2, 6 -> {
                    start = 3;
                    lenght = 3;
                }
                case 3, 4 -> {
                    start = 1;
                    lenght = 7;
                }
                case 5 -> {
                    start = 2;
                    lenght = 6;
                }
                case 7 -> {
                    start = 3;
                    lenght = 2;
                }
                case 8 -> {
                    start = 0;
                    lenght = 0;
                }
            }
            for (int j = 0; j < 9; j++) {
                Coordinates key = new Coordinates(i,j);
                while(j>=start && j<start+lenght){
                    this.grid.put(key, value);
                    value++;
                }
            }
        }
        //fill board with card the first time
        this.fill(bag);
    }

    //returns a boolean representing the necessity of refilling the board with cards
    private boolean cardCheck(){
        Coordinates AUXkey = new Coordinates();
        boolean flag=true;
        for(int i=0 ; i<9 ; i++){
            AUXkey.setX(i);
            for(int j=0 ; j<9 ; j++){
                AUXkey.setY(j);
                if(board[grid.get(AUXkey)]!=null && /*controllo adiacenti*/){
                    flag=false; //caso in cui ci sono adiacenti
                }
            }
        }
        return flag;
    }

    private void updatePickables(){
        //refreshes the state of the cards
    }

    private void updatePickablesAtFirst(){
        Coordinates AUXkey = new Coordinates();
        for (int i = 1; i < 9; i++) {
           AUXkey.setX(i);
            switch (i) {
                case 1 -> {
                    AUXkey.setY(3);
                    board[grid.get(AUXkey)].setState(Card.State.PICKABLE);
                    AUXkey.setY(4);
                    board[grid.get(AUXkey)].setState(Card.State.PICKABLE);
                }
                case 2, 6 -> {
                    AUXkey.setY(3);
                    board[grid.get(AUXkey)].setState(Card.State.PICKABLE);
                    AUXkey.setY(5);
                    board[grid.get(AUXkey)].setState(Card.State.PICKABLE);
                }
                case 3 -> {
                    AUXkey.setY(2);
                    board[grid.get(AUXkey)].setState(Card.State.PICKABLE);
                    AUXkey.setY(6);
                    board[grid.get(AUXkey)].setState(Card.State.PICKABLE);
                    AUXkey.setY(7);
                    board[grid.get(AUXkey)].setState(Card.State.PICKABLE);
                }
                case 4 -> {
                    AUXkey.setY(1);
                    board[grid.get(AUXkey)].setState(Card.State.PICKABLE);
                    AUXkey.setY(7);
                    board[grid.get(AUXkey)].setState(Card.State.PICKABLE);
                }
                case 5 -> {
                    AUXkey.setY(1);
                    board[grid.get(AUXkey)].setState(Card.State.PICKABLE);
                    AUXkey.setY(2);
                    board[grid.get(AUXkey)].setState(Card.State.PICKABLE);
                    AUXkey.setY(6);
                    board[grid.get(AUXkey)].setState(Card.State.PICKABLE);
                }
                case 7 -> {
                    AUXkey.setY(4);
                    board[grid.get(AUXkey)].setState(Card.State.PICKABLE);
                    AUXkey.setY(5);
                    board[grid.get(AUXkey)].setState(Card.State.PICKABLE);
                }
            }
        }
    }
}
