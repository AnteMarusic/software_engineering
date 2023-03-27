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
        Card tmp = new Card(board[grid.get(coor)].getColor() , board[grid.get(coor)].getState());
        this.removeCardAtCoordinate(coor);
        return tmp;
    }

    //remove a card from the board and set the reference to null, and updates adjacent
    private void removeCardAtCoordinate(Coordinates coor){
        int x = coor.getX();
        int y = coor.getY();
        Coordinates AUXcoor = new Coordinates(x,y-1);
        if (board[grid.get(AUXcoor)]!= null)
            board[grid.get(AUXcoor)].setState(Card.State.PICKABLE);
        AUXcoor.setY(y+1);
        if (board[grid.get(AUXcoor)]!= null)
            board[grid.get(AUXcoor)].setState(Card.State.PICKABLE);
        AUXcoor.setXY(x-1, y);
        if (board[grid.get(AUXcoor)]!= null)
            board[grid.get(AUXcoor)].setState(Card.State.PICKABLE);
        AUXcoor.setX(x+1);
        if (board[grid.get(AUXcoor)]!= null)
            board[grid.get(AUXcoor)].setState(Card.State.PICKABLE);
        board[grid.get(coor)] = null;
    }
    //probably useless, we'll check during class Game implementation
    public void boardProcedure(){
        //....
    }

    //checks whether the board needs to be refreshed/refilled

    abstract boolean cardCheck();

    // //refreshes the state of the cards once the board gets filled/refilled
    abstract void updatePickablesAtFirst();
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
        int length;
        int value=0;
        this.grid = new HashMap<Coordinates, Integer>();
        for (int i = 1; i < 9; i++) {
            switch (i) {
                case 1 -> {
                    start = 4;
                    length = 2;
                }
                case 2, 6 -> {
                    start = 3;
                    length = 3;
                }
                case 3 -> {
                    start = 1;
                    length = 6;
                }
                case 4 -> {
                    start = 1;
                    length = 7;
                }
                case 5 -> {
                    start = 2;
                    length = 6;
                }
                case 7 -> {
                    start = 3;
                    length = 2;
                }
                case 8 -> {
                    start = 0;
                    length = 0;
                }
            }
            for (int j = 0; j < 9; j++) {
                Coordinates key = new Coordinates(i,j);
                while(j>=start && j<start+length){
                    this.grid.put(key, value);
                    value++;
                }
            }
        }
        //fill board with card the first time
        this.fill(bag);
        this.updatePickablesAtFirst();
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
    private boolean cardCheck() {
        int start, length;
        Coordinates AUXkey = new Coordinates();
        for (int i = 1; i < 9; i++) {
            AUXkey.setX(i);
            switch (i) {
                case 1 -> {
                    start = 3;
                    length = 2;
                }
                case 2, 6 -> {
                    start = 3;
                    length = 3;
                }
                case 3 -> {
                    start = 2;
                    length = 6;
                }
                case 4 -> {
                    start = 1;
                    length = 7;
                }
                case 5 -> {
                    start = 1;
                    length = 6;
                }
                case 7 -> {
                    start = 4;
                    length = 2;
                }
            }
            for (int j = 1; j < 9; j++) {
                while (j >= start && j < start + length) {
                    AUXkey.setXY(i, j);
                    if (board[grid.get(AUXkey)] != null) {
                        if (board[grid.get(AUXkey)] != null){
                            return false;
                        }
                        AUXkey.setY(j + 1);
                        if (board[grid.get(AUXkey)] != null){
                            return false;
                        }
                        AUXkey.setXY(i - 1,j);
                        if (board[grid.get(AUXkey)] != null){
                            return false;
                        }
                        AUXkey.setX(i + 1);
                        if (board[grid.get(AUXkey)] != null){
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }
}
