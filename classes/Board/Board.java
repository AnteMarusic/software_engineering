package org.example;

import java.util.HashMap;

abstract class Board {
    protected BagOfCards bag;
    protected HashMap<Coordinates, Card> board;
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


    //fills board with new card taken from the bag
    abstract void fill(BagOfCards bag);

    //return card in position Coordinate coor
    private Card getCardAtCoordinate(Coordinates coor){
        Card tmp = new Card(board.get(coor).getColor() , board.get(coor).getState());
        this.removeCardAtCoordinate(coor);
        return tmp;
    }

    //remove a card from the board and set the reference to null, and updates adjacent
    private void removeCardAtCoordinate(Coordinates coor){
        int x = coor.getX();
        int y = coor.getY();
        Coordinates AUXcoor = new Coordinates(x,y-1);
        if (y-1>0 && board.get(AUXcoor)!= null)
            board.get(AUXcoor).setState(Card.State.PICKABLE);
        AUXcoor.setY(y+1);
        if (y+1<9 && board.get()]!= null)
        board.get(AUXcoor).setState(Card.State.PICKABLE);
        AUXcoor.setXY(x-1, y);
        if (x-1>0 && board.get(AUXcoor)!= null)
            board.get(AUXcoor).setState(Card.State.PICKABLE);
        AUXcoor.setX(x+1);
        if (x+1<9 && board.get(AUXcoor)!= null)
            board.get(AUXcoor).setState(Card.State.PICKABLE);
        board.get(AUXcoor) = null;
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
