package org.polimi.client;

import org.polimi.server.model.Card;
import org.polimi.server.model.Coordinates;

import java.util.Map;

public class GameEnv {
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
}
