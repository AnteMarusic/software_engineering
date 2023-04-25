package org.polimi.server.model;

import org.polimi.GameRules;

import java.util.HashMap;
import java.util.Map;

public class Board {
    private final static int ROW = 9;
    private final BagOfCards bag;
    private final Map<Coordinates, Card> board;
    private final int numOfPlayers;

    public Board(int numOfPlayers, BagOfCards bag) {
        this.numOfPlayers = numOfPlayers;
        this.bag = bag;
        this.board = new HashMap<>();
        this.fill();
    }

    //fills the board with new cards taken from the bag, and sets border-cards' state to PICKABLE, else to NOT_PICKABLE
    public void fill() {
        int start, length;
        for (int i = 0; i < 9; i++) {
            int[] arr= GameRules.getCorrectStartAndLength(i, numOfPlayers);
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
            return this.removeCardAtCoordinate(coordinates);
        }
    }

    /**
     * is an observer method
     * @param coordinates coordinates of the card you want to see
     * @return null if no card is found at those coordinates or the coordinates are invalid
     */
    public Card seeCardAtCoordinates(Coordinates coordinates) {
        return this.board.get(coordinates);
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
        int start, length ;
        Coordinates AUXkey = new Coordinates(0, 0);
        Coordinates[] AdjacentCoords = new Coordinates[4];
        for (int i = 0; i < 9; i++) {
            int[] arr= GameRules.getCorrectStartAndLength(i, numOfPlayers);
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

    public void printBoard() {
        int start, length;
        int[] temp;
        Card card;
        for (int row = 0; row < ROW; row ++) {
            temp = GameRules.getCorrectStartAndLength(row, numOfPlayers);
            start = temp [0];
            length = temp[1];
            for (int i = 0; i < start; i ++) {
                System.out.print(" ");
            }
            for (int col = start; col < length + start; col ++) {
                card = seeCardAtCoordinates(new Coordinates(row, col));
                if(card == null)
                    System.out.print("N");
                else
                    System.out.print(card.convertColorToChar());
            }
            System.out.println(" ");
        }
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

    public void printMap() {
        this.board.forEach((key, value) -> System.out.println(key + " " + value));
    }
}