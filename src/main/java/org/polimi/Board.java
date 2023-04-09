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
            int[] arr= getCorrectStartAndLength(i);
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


    /**
     * this method allows to get a card from the board
     * @param coordinates coordinates of the card you want to get
     * @return null if no card is found at the coordinates provided or those are invalid or the card is NOT_PICKABLE.
     * The actual card otherwise
     */
    public Card getCardAtCoordinates(Coordinates coordinates) {
        Card temp = this.board.get(coordinates);
        if(temp == null)
            return null;
        else {
            if (temp.getState() == Card.State.NOT_PICKABLE)
                return null;
            Card tmp = new Card(this.board.get(coordinates).getColor(), this.board.get(coordinates).getState());
            this.removeCardAtCoordinate(coordinates);
            return tmp;
        }
    }

    /**
     * is an observer method
     * @param coordinates coordinates of the card you want to see
     * @return null if no card is found at those coordinates or the coordinates are invalid
     */
    public Card seeCardAtCoordinates(Coordinates coordinates) {
        Card temp = this.board.get(coordinates);
        if (temp == null)
            return null;
        else {
            return removeCardAtCoordinate(coordinates);
        }
    }

    //removes a card from the board and updates each adjacent card's state to PICKABLE, if present
    private Card removeCardAtCoordinate(Coordinates coordinates) {
        int x = coordinates.getRow();
        int y = coordinates.getCol();
        Coordinates[] AdjacentCoordinates = new Coordinates[4];
        AdjacentCoordinates[0] = new Coordinates(x, y + 1);
        AdjacentCoordinates[1] = new Coordinates(x + 1, y);
        AdjacentCoordinates[2] = new Coordinates(x, y - 1);
        AdjacentCoordinates[3] = new Coordinates(x - 1, y);
        for (int i = 0; i < 4; i++) {
            if (AdjacentCoordinates[i].CoordinatesAreValid() && board.get(AdjacentCoordinates[i]) != null) {
                board.get(AdjacentCoordinates[i]).setState(Card.State.PICKABLE);
            }
        }
        return this.board.remove(coordinates);
    }


    //checks whether the board needs to be refreshed/refilled
    public boolean refillCheck() {
        int start , length ;
        Coordinates AUXkey = new Coordinates(0, 0);
        Coordinates[] AdjacentCoords = new Coordinates[4];
        for (int i = 0; i < 9; i++) {
            int[] arr= getCorrectStartAndLength(i);
            start = arr[0];
            length = arr[1];
            for (int j = 0; j < 9; j++) {
                if (j >= start && j < start + length) {
                    AUXkey.setRowCol(i, j);
                    if (board.get(AUXkey) != null) {
                        AdjacentCoords[0] = new Coordinates(i, j + 1);
                        AdjacentCoords[1] = new Coordinates(i + 1, j);
                        AdjacentCoords[2] = new Coordinates(i, j - 1);
                        AdjacentCoords[3] = new Coordinates(i - 1, j);
                        for (int k = 0; k < 4; k++) {
                            if (AdjacentCoords[i].CoordinatesAreValid() && board.get(AdjacentCoords[i]) != null) {
                                return false;
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    private int[] getCorrectStartAndLength(int row) {
        int start=-1, length=-1;
        int[] arrayOfInt = new int[2];
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
        arrayOfInt[0]= start;
        arrayOfInt[1]= length;
        return arrayOfInt;
    }

    private boolean CornerCases(int i, int j){
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