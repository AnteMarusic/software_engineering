package org.polimi.client;

import org.polimi.server.model.Board;
import org.polimi.server.model.Bookshelf;
import org.polimi.server.model.Card;
import org.polimi.server.model.Coordinates;

import java.util.Map;

public class GameEnv {
    public static final String ANSI_RESET = "\u001B[0m";

    public static final String ANSI_PINK = "\u001B[35m"; //PINK
    public static final String ANSI_GREEN = "\u001B[32m"; //GREEN
    public static final String ANSI_WHITE = "\u001B[37m"; //WHITE
    public static final String ANSI_ORANGE = "\u001B[31m"; //ORANGE
    public static final String ANSI_CYAN = "\u001B[36m"; //CYAN
    public static final String ANSI_BLUE = "\u001B[34m"; //BLUE

    private ClientBoard clientBoard;
    private ClientBookshelf[] clientBookshelf;
    private int numOfPlayers;
    private final String[] players;
    private final int me; //my player index

    public GameEnv(String[] players, int me, int numOfPlayers){
        this.players = players;
        this.me = me;
        this.numOfPlayers = numOfPlayers;
        for(int i=0 ; i<numOfPlayers ; i++){
            clientBookshelf[i] = new ClientBookshelf();
        }
    }
    public void setClientBoard(Map<Coordinates, Card> clientBoard){
        this.clientBoard = new ClientBoard(clientBoard, this.numOfPlayers);
    }

    public void printEnv(){
        this.clientBoard.printMap();
        this.clientBookshelf[this.me].printMyBookshelf();
        for(int i=0 ; i<this.numOfPlayers ; i++){
            if(i!=me){
                System.out.println(this.players[i]);
                this.clientBookshelf[i].print();
            }
        }
    }


    public int getMaxInsertable() {
        return clientBookshelf[me].getMaxInsertable();
    }

    public int getNumOfPlayers() {
        return numOfPlayers;
    }

    public Card boardSeeCardAtCoordinates (Coordinates coordinates) {
        return clientBoard.seeCardAtCoordinates(coordinates);
    }

    public boolean isCardPickable (Coordinates coordinates) {
        return clientBoard.seeCardAtCoordinates(coordinates) != null && clientBoard.seeCardAtCoordinates(coordinates).getState() == Card.State.PICKABLE;
    }

    public static void main(String[] args){
        Board board = new Board(2);
        ClientBoard clientBoard = new ClientBoard(board.getGrid() , 2);
        Bookshelf bookshelf = new Bookshelf();
        clientBoard.printMap();
    }
}
