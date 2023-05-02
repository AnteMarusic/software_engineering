package org.polimi.client;

import org.polimi.GameRules;
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

    public Card seeCardAtCoordinates(Coordinates coordinates) {
        return this.board.get(coordinates);
    }

    public void printMap() {
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

}
