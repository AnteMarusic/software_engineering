package org.polimi;

import java.util.HashMap;

public class Board {
    private final BagOfCards bag;
    private final HashMap<Coordinates, Card> board;
    private final int numOfPlayers;

    //board constructor
    public Board(int numOfPlayers, BagOfCards bag) {
        this.numOfPlayers = numOfPlayers;
        this.bag = bag;
        this.board = new HashMap<Coordinates, Card>();
        this.fill();
    }

    //fills the board with new cards taken from the bag, and sets border-cards' state to PICKABLE, else to NOT_PICKABLE
    public void fill() {
        int start, length;
        for (int i = 0; i < 9; i++) {
            int[] arr= getCorrectStartandLength(i);
            start = arr[0];
            length = arr[1];
            for (int j = 0; j < 9; j++) {
                Coordinates AUXkey = new Coordinates(i, j);
                if (j >= start && j < start + length) {
                    this.board.put(AUXkey, bag.collectCard());
                    if(j==start || j==start+length-1 || CornerCases(i,j))
                        this.board.get(AUXkey).setState(Card.State.PICKABLE);
                    else
                        this.board.get(AUXkey).setState(Card.State.NOT_PICKABLE);
                }
            }
        }
    }


    //returns the card found at the relative coordinates "coor"
    public Card getCardAtCoordinate(Coordinates coor) {
        Card tmp = new Card(this.board.get(coor).getColor(), this.board.get(coor).getState());
        this.removeCardAtCoordinate(coor);
        return tmp;
    }

    //removes a card from the board and updates each adjacent card's state to PICKABLE, if present
    private void removeCardAtCoordinate(Coordinates coor) {
        int x = coor.getX();
        int y = coor.getY();
        Coordinates[] Adjacentcoords = new Coordinates[4];
        Adjacentcoords[0] = new Coordinates(x, y + 1);
        Adjacentcoords[1] = new Coordinates(x + 1, y);
        Adjacentcoords[2] = new Coordinates(x, y - 1);
        Adjacentcoords[3] = new Coordinates(x - 1, y);
        for (int i = 0; i < 4; i++) {
            if (Adjacentcoords[i].CoordsAreValid() && board.get(Adjacentcoords[i]) != null) {
                board.get(Adjacentcoords[i]).setState(Card.State.PICKABLE);
            }
        }
        this.board.remove(coor);
    }


    //checks whether the board needs to be refreshed/refilled
    public boolean cardCheck() {
        int start , length ;
        Coordinates AUXkey = new Coordinates(0, 0);
        Coordinates[] Adjacentcoords = new Coordinates[4];
        for (int i = 0; i < 9; i++) {
            int[] arr= getCorrectStartandLength(i);
            start = arr[0];
            length = arr[1];
            for (int j = 0; j < 9; j++) {
                if (j >= start && j < start + length) {
                    AUXkey.setXY(i, j);
                    if (board.get(AUXkey) != null) {
                        Adjacentcoords[0] = new Coordinates(i, j + 1);
                        Adjacentcoords[1] = new Coordinates(i + 1, j);
                        Adjacentcoords[2] = new Coordinates(i, j - 1);
                        Adjacentcoords[3] = new Coordinates(i - 1, j);
                        for (int k = 0; k < 4; k++) {
                            if (Adjacentcoords[i].CoordsAreValid() && board.get(Adjacentcoords[i]) != null) {
                                return false;
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    public int[] getCorrectStartandLength(int row) {
        int start=-1, length=-1;
        int[] arrayofint = new int[2];
        switch (row) {
            case 0 -> {
                start = 3;
                length = 2;
                if (this.numOfPlayers < 4) {
                    length--;
                    if (this.numOfPlayers < 3) {
                        length--;
                    }
                }
            }
            case 1 -> {
                start = 3;
                length = 3;
                if (this.numOfPlayers < 4) {
                    length--;
                }
            }
            case 2, 6 -> {
                start = 2;
                length = 5;
                if (this.numOfPlayers < 3) {
                    start++;
                    length--;
                }
            }
            case 3 -> {
                start = 1;
                length = 8;
                if (this.numOfPlayers < 4) {
                    start++;
                    length--;
                    if (this.numOfPlayers < 3) {
                        length--;
                    }
                }
            }
            case 4 -> {
                start = 0;
                length = 9;
                if (this.numOfPlayers < 4) {
                    start++;
                    length--;
                }
            }
            case 5 -> {
                start = 0;
                length = 8;
                if (this.numOfPlayers < 4) {
                    length--;
                    if (this.numOfPlayers < 3) {
                        start++;
                        length--;
                    }
                }
            }
            case 7 -> {
                start = 3;
                length = 3;
                if (this.numOfPlayers < 4) {
                    start++;
                    length--;
                }
            }
            case 8 -> {
                start = 4;
                length = 2;
                if (this.numOfPlayers < 4) {
                    start++;
                    length--;
                    if (this.numOfPlayers < 3) {
                        length--;
                    }
                }
            }
        }
        arrayofint[0]= start;
        arrayofint[1]= length;
        return arrayofint;
    }

    public boolean CornerCases(int i, int j){
        switch(numOfPlayers){
            case 2 -> {
                return i==3&&j==6 || i==5&&j==2;
            }
            case 3 -> {
                return i==2&&j==5 || i==3&&j==7 || i==5&&j==1 || i==6&&j==3;
            }
            case 4 -> {
                return i==3&&j==7 || i==5&&j==1;
            }
        }
        return false;//default
    }
}