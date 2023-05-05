package org.polimi.server.controller;

import org.polimi.messages.Message;
import org.polimi.messages.MessageType;
import org.polimi.messages.RankingMessage;
import org.polimi.messages.StartGameMessage;
import org.polimi.server.ClientHandler;
import org.polimi.server.model.Coordinates;
import org.polimi.server.model.Game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class GameController {
    private final ArrayList<ClientHandler> players = new ArrayList<ClientHandler>();
    private final int numOfPlayers;
    private int currentPlayer;
    private final int firstPlayer;
    private final Game game;


    public GameController(ArrayList<ClientHandler> list){
        numOfPlayers = list.size();
        players.addAll(list);
        firstPlayer = setFirstPlayer(numOfPlayers);
        currentPlayer=firstPlayer;
        String[] player = new String[numOfPlayers];
        for(int i=0; i<list.size(); i++){
            player[i]=list.get(i).getUsername();
        }
        game = new Game(numOfPlayers, firstPlayer,player);
        initGameEnv();
        startGameTurn();

    }
    private void initGameEnv(){
        // manda a tutti clienhandler un messaggio in cui dice che il gioco sta iniziando e con chi sta giocando
        String[] usernames = new String[numOfPlayers];
        for(int i=0; i<numOfPlayers; i++){
            usernames[i] = players.get(i).getUsername();
        }
        for(ClientHandler player : players ){
            player.sendMessage(new StartGameMessage("server", usernames));
        }
        // manda a tutti la bord gli shared goal e a ogni client il proprio private goal
    }
    private void startGameTurn(){
        players.get(currentPlayer).sendMessage(new Message("server", MessageType.CHOOSE_CARDS_REQUEST));
        // comunica al primo giocatore di iniziare scegliendo le carte da rimuovere dalla board
    }
    public void removeCards (List<Coordinates> coordinates){

        //manda ad ogni clientHandler la lista di coordinate da rimuovere nelle varie board personali, a tutti tranne al currentPlayer che se le aggiorna da solo lato client
        game.remove(coordinates);
    }
    public void insertInBookshelf (int column){
        int currentPoints = game.insertInBookshelf(column, currentPlayer);
        // manda al giocatore corrente il punteggio attuale
    }
    public void notifyNextPlayer(){
        // se Game.endGame è true e il prossimo giocatore è il firstPlayer
        // chiama il metodo endGame (passandogli l'indice del primo a completare la bookshelf) ed esce da questo metodo

        if( game.getEndGame() && currentPlayer==((firstPlayer-1)%numOfPlayers)){
            endGame();
            return;
        }
        // In caso non esca
        // manda un messaggio a tutti dicendo chi è il giocatore successivo
        // manda un messaggio di richiesta carte al giocatore successivo
        else{
            nextPlayer();
            players.get(currentPlayer).sendMessage(new Message("server", MessageType.CHOOSE_CARDS_REQUEST));
        }

    }

    private void endGame(){
        HashMap<String,Integer> gameRanking = game.endGame();
        gameAwarding(gameRanking);
    }
    private void gameAwarding (HashMap<String,Integer> ranking){
        // mando a tutti un messaggio contenente la classifica
        for(ClientHandler player : players ){
            player.sendMessage(new RankingMessage("server", ranking));
        }
    }



    private void nextPlayer(){
        currentPlayer = (currentPlayer+1) % players.size();
    }

    public void reconnect (ClientHandler clientHandler){}

    public void EndGame(int ender){

    }
    private int setFirstPlayer(int numOfPlayer){
        Random random = new Random();
        return random.nextInt(numOfPlayer);
    }



}
