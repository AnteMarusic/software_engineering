package org.polimi.client;

import org.polimi.server.model.Card;
import org.polimi.server.model.Coordinates;

import java.util.HashMap;
import java.util.Map;

public class GameEnv {
    private ClientBoard board;
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


    public void setBoard(Map<Coordinates, Card> board){
        this.board = new ClientBoard(board, this.numOfPlayers);
    }

    public void printEnv(){
        this.board.printMap();
        this.clientBookshelf[this.me].printMyBookshelf();
        for(int i=0 ; i<this.numOfPlayers ; i++){
            if(i!=me){
                System.out.println(this.players[i]);
                this.clientBookshelf[i].print();
            }
        }
    }


}
