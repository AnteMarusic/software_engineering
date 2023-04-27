package org.polimi.client;

import org.polimi.server.model.Card;
import org.polimi.server.model.Coordinates;

import java.util.Map;
import org.polimi.server.model.*;

public class ClientBoard {
    private final static int ROW = 9;
    private Map <Coordinates, Card> board;
    private final int numOfPlayers;

    public ClientBoard(Map <Coordinates, Card> board, int numOfPlayers) {
        this.board = board;
        this.numOfPlayers = numOfPlayers;
    }


    public void setMap(Map<Coordinates, Card> board){
        this.board = board;
    }

    public void printMap() {
        int start, length;
        int[] temp;
        Card card;
        for (int row = ROW; row >=0; row --) {
            temp = getCorrectStartAndLength(row);
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
    private Card seeCardAtCoordinates(Coordinates coordinates) {
        return this.board.get(coordinates);
    }

}
