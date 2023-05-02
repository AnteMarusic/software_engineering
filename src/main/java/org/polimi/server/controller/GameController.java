package org.polimi.server.controller;

import org.polimi.server.ClientHandler;
import org.polimi.server.model.Coordinates;
import org.polimi.server.model.Game;

import java.util.ArrayList;
import java.util.List;

public class GameController {
    boolean endGame = false;
    private final ArrayList<ClientHandler> players = new ArrayList<ClientHandler>();
    //private final int numOfPlayers;
    private int currentPlayer;
    private int firstPlayer;
    private Game game;

    public GameController(List<ClientHandler> list){
        game = new Game(list.size(), firstPlayer);
        initGameEnv();
        startGameTurn();

    }
    public void removeCards (List<Coordinates> coordinates){

        // manda ad ogni clientHandler la lista di coordinate da rimuovere nelle varie board personali, a tutti tranne al sender che se le aggiorna da solo lato client
        //game.remove
    }
    public void insertInBookshelf (int column){
        // inserisce, controlla se la bookshelf è piena e controlla gli obiettivi
    }
    public void notifyNextPlayer(){
        // manda un messaggio a tutti dicendo chi è il giocatore successivo
        // manda un messaggio di richiesta carte al giocatore successivo
    }

    private void nextPlayer(){
        currentPlayer = (currentPlayer+1) % players.size();
    }

    public void reconnect (ClientHandler clientHandler){}

    public void EndGame(int ender){

    }
    public void setFirstPlayer(int firstPlayer){
        this.firstPlayer = firstPlayer;
        currentPlayer = firstPlayer;
    }
    private void initGameEnv(){
        // manda a tutti la bord gli shared goal e ad ogni client il proprio private goal
    }

    private void startGameTurn(){
        // comunica al primo giocatore di iniziare
    }
}
