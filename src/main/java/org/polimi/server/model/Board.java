package org.polimi.server.model;

import org.polimi.GameRules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.polimi.GameRules.boardRowColInBound;

public class Board {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_CYAN = "\u001B[36m";
    private final static int ROW = 9;
    private final BagOfCards bag;
    private final Map<Coordinates, Card> board;
    private final int numOfPlayers;


    public Board(int numOfPlayers) {
        this.numOfPlayers = numOfPlayers;
        this.bag = new BagOfCards();
        this.board = new HashMap<>(45);
        this.fill();
    }

    public Map<Coordinates, Card> getGrid(){
        return this.board;
    }

    /**
     * fills the board with new cards taken from the bag, and sets border-cards' state to PICKABLE, otherwise to NOT_PICKABLE
     */
    private void fill() {
        int start, length;
        for (int i = 0; i < 9; i++) {
            int[] arr= GameRules.getCorrectStartAndLength(i, numOfPlayers);
            start = arr[0];
            length = arr[1];
            for (int j = 0; j < 9; j++) {
                Coordinates AUXkey = new Coordinates(i, j);
                if (j >= start && j < start + length) {
                    this.board.put(AUXkey, bag.collectCard());
                    if(j==start || j==start+length-1 || CornerCases(i,j)){
                        this.board.get(AUXkey).setState(Card.State.PICKABLE);
                        //debug println
                        System.out.println(i + " " + j);
                    }
                    else
                        this.board.get(AUXkey).setState(Card.State.NOT_PICKABLE);
                }
            }
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
     * removes a card from the board and updates each adjacent card's state to PICKABLE, if present
     */
    private Card removeCardAtCoordinate(Coordinates coordinates) {
        int x = coordinates.getRow();
        int y = coordinates.getCol();
        Coordinates[] AdjacentCoordinates = new Coordinates[4];
        AdjacentCoordinates[0] = new Coordinates(x, y + 1);
        AdjacentCoordinates[1] = new Coordinates(x + 1, y);
        AdjacentCoordinates[2] = new Coordinates(x, y - 1);
        AdjacentCoordinates[3] = new Coordinates(x - 1, y);
        for (int i = 0; i < 4; i++) {
            if (boardRowColInBound(AdjacentCoordinates[i].getRow(), AdjacentCoordinates[i].getCol(), numOfPlayers) && board.get(AdjacentCoordinates[i]) != null) {
                board.get(AdjacentCoordinates[i]).setState(Card.State.PICKABLE);

            }
        }
        return this.board.remove(coordinates);
    }


    /**
     * checks whether the board needs to be refilled
     */
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
                    AUXkey = new Coordinates(i,j);
                    if (board.get(AUXkey) != null) {
                        AdjacentCoords[0] = new Coordinates(i, j + 1);
                        AdjacentCoords[1] = new Coordinates(i + 1, j);
                        AdjacentCoords[2] = new Coordinates(i, j - 1);
                        AdjacentCoords[3] = new Coordinates(i - 1, j);
                        for (int k = 0; k < 4; k++) {
                            if (boardRowColInBound(AdjacentCoords[i].getRow(), AdjacentCoords[i].getCol(), numOfPlayers) && board.get(AdjacentCoords[i]) != null) {
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

    public void print() {
        int start, length;
        int[] temp;
        Card card;
        for (int row = ROW; row >=0; row --) {
            temp = GameRules.getCorrectStartAndLength(row, numOfPlayers);
            start = temp [0];
            length = temp[1];
            //stampa prima riga "+—+"
            for (int i = 0; i < start; i++) {
                System.out.print("    ");
            }
            for (int col = start; col < length + start; col ++) {
                System.out.print("+—+ ");
            }
            System.out.println();

            //stampa seconda riga, colori
            for (int i = 0; i < start; i++) {
                System.out.print("    ");
            }
            for (int col = start; col < length + start; col ++) {
                card = seeCardAtCoordinates(new Coordinates(row, col));
                if(card == null)
                    System.out.print("|N| ");
                else
                    System.out.print("|"+card.convertColorToChar()+"| ");
            }
            System.out.println();

            //stampa terza riga "+—+"
            for (int i = 0; i < start; i++) {
                System.out.print("    ");
            }
            for (int col = start; col < length + start; col ++) {
                System.out.print("+—+ ");
            }
            System.out.println();
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