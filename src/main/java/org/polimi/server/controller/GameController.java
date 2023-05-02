package org.polimi.server.controller;

import org.polimi.server.ClientHandler;
import org.polimi.server.model.Coordinates;

import java.util.ArrayList;
import java.util.List;

public class GameController {
    boolean endGame = false;
    private final ArrayList<ClientHandler> players = new ArrayList<ClientHandler>();
    //private final int numOfPlayers;

    public GameController(List<ClientHandler> list){

    }
    public void removeCards (List<Coordinates> coordinates, ClientHandler sender){
        // manda ad ogni clientHandler la lista di coordinate da rimuovere nelle varie board personali, a tutti tranne al sender che se le aggiorna da solo lato client
    }



    public void reconnect (ClientHandler clientHandler){}
}
