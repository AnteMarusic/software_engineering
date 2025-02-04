package org.polimi.client;

import org.polimi.GameRules;
import org.polimi.servernetwork.model.Card;
import org.polimi.servernetwork.model.Coordinates;

import java.util.Map;

public class ClientBoard {
    public static final String ANSI_RESET = "\u001B[0m";

    public static final String ANSI_PINK = "\u001B[35m"; //PINK
    public static final String ANSI_GREEN = "\u001B[32m"; //GREEN
    public static final String ANSI_WHITE = "\u001B[37m"; //WHITE
    public static final String ANSI_ORANGE = "\u001B[31m"; //ORANGE
    public static final String ANSI_CYAN = "\u001B[36m"; //CYAN
    public static final String ANSI_BLUE = "\u001B[34m"; //BLUE
    private final static int DIM = 9;
    private Map <Coordinates, Card> board;
    private final int numOfPlayers;

    public ClientBoard(Map <Coordinates, Card> board, int numOfPlayers) {
        this.board = board;
        this.numOfPlayers = numOfPlayers;
    }

    public Card removeCardAtCoordinates (Coordinates coordinates) {
        return this.board.remove(coordinates);
    }
    public void setToPickable(Coordinates coordinates){
        this.board.get(coordinates).setState(Card.State.PICKABLE);
    }
    public void setMap(Map<Coordinates, Card> board){
        this.board = board;
    }


    /**
     * Retrieves the card at the specified coordinates on the game board.
     *
     * @param coordinates the coordinates of the card
     * @return the card at the specified coordinates, or null if no card is found
     */
    public Card seeCardAtCoordinates(Coordinates coordinates) {
        return this.board.get(coordinates);
    }

    /**
     * Prints the game board to the console.
     */
    public void printBoard() {
        int start, length;
        int[] temp;
        Card card;
        System.out.print("    ");
        for(int i = 0; i< DIM; i++){
            System.out.print(" " + i + "  ");
        }
        System.out.println();

        // da sistemare
        for (int row = 0; row < DIM; row ++) {
            temp = GameRules.getCorrectStartAndLength(row, numOfPlayers);
            start = temp [0];
            length = temp[1];
            if(row==0){
                System.out.print("   ");
                for (int i = 0; i < start; i++) {
                    System.out.print("    ");
                }
                System.out.print(" ");
                for (int i=start; i< start + length; i++) {
                    System.out.print("+—+ ");
                }
                System.out.println();
            }
            //stampa seconda riga, colori
            System.out.print(row);
            System.out.print("   ");
            for (int i = 0; i < start; i++) {
                System.out.print("    ");
            }
            for (int col = start; col < length + start; col ++) {
                card = seeCardAtCoordinates(new Coordinates(row, col));
                if(card == null)
                    System.out.print("| | ");
                else {
                    switch(card.getColor()) {
                        case WHITE -> {
                            System.out.print("|");
                            System.out.print(ANSI_WHITE+ "□"+ANSI_RESET);
                            System.out.print("| ");
                        }
                        case BLUE -> {
                            System.out.print("|");
                            System.out.print(ANSI_BLUE+ "□"+ ANSI_RESET);
                            System.out.print("| ");
                        }
                        case ORANGE -> {
                            System.out.print("|");
                            System.out.print(ANSI_ORANGE+"□"+ANSI_RESET);
                            System.out.print("| ");
                        }
                        case PINK -> {
                            System.out.print("|");
                            System.out.print(ANSI_PINK+"□"+ANSI_RESET);
                            System.out.print("| ");
                        }
                        case CYAN -> {
                            System.out.print("|");
                            System.out.print(ANSI_CYAN+"□"+ANSI_RESET);
                            System.out.print("| ");
                        }
                        case GREEN -> {
                            System.out.print("|");
                            System.out.print(ANSI_GREEN+"□"+ANSI_RESET);
                            System.out.print("| ");
                        }
                    }
                }
            }
            System.out.print("\n");

            //stampa terza riga "+—+"
            temp = GameRules.getCorrectStartAndLength(row+1, numOfPlayers);
            start = temp [0];
            length = temp[1];
            System.out.print("   ");
            for (int i = 0; i < start; i++) {
                System.out.print("    ");
            }
            System.out.print(" ");
            for (int col = start; col < length + start; col++) {
                System.out.print("+—+ ");
            }
            System.out.println();

        }
    }

}
