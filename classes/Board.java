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


    private boolean cardCheck(){
        //metodo che controlla se é necessario effettuare
        //una estrazione di carte
    }

    private void updatePickables(){
        //refreshes the state of the cards
    }
}
