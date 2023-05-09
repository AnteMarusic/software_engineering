package org.polimi.server.controller;

import org.polimi.messages.*;
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
        game = new Game(numOfPlayers, firstPlayer,getPlayersUsername());
        initGameEnv();
        startGameTurn();

    }
    private void initGameEnv(){
        // manda a tutti clienhandler un messaggio in cui dice che il gioco sta iniziando e con chi sta giocando
        // manda a tutti la bord gli shared goal e a ogni client il proprio private goal
        String[] usernames = new String[numOfPlayers];
        usernames = getPlayersUsername();
        for(int i=0; i<players.size();i++){
            players.get(i).sendMessage(new StartGameMessage("server", usernames));
            players.get(i).sendMessage(new ModelStatusAllMessage("server", game.getBoardMap(), game.getBookshelfMap(i), game.getIndexSharedGoal1(), game.getIndexSharedGoal2(), game.getPersonalGoalIndex(i), usernames ));
        }

    }
    private void startGameTurn(){
        players.get(currentPlayer).sendMessage(new Message("server", MessageType.CHOOSE_CARDS_REQUEST));
        // comunica al primo giocatore d'iniziare scegliendo le carte da rimuovere dalla board
    }

    /**
     * removes the card from the board, stores them in game for bookshelf insertion.
     * sends a message to all the players containing the coordinates of the cards to remove and the coordinates
     * of the card to update to pickable
     * @param coordinates coordinates to remove (sent by the client)
     */
    public void removeCards (List<Coordinates> coordinates){
        game.remove(coordinates);
        for(ClientHandler c : players) {
            c.sendMessage(new CardToRemove("server", coordinates, game.geToUpdateToPickable()));
        }
    }
    public void insertInBookshelf (int column){
        int currentPoints = game.insertInBookshelf(column, currentPlayer);
        // manda al giocatore corrente il punteggio attuale
        players.get(currentPoints).sendMessage(new CurrentScore("server", currentPoints));
    }
    public void notifyNextPlayer(){
        // se Game.endGame è true e il prossimo giocatore è il firstPlayer
        // chiama il metodo endGame ed esce da questo metodo

        if(game.getEndGame() && currentPlayer==((firstPlayer-1)%numOfPlayers)){
            endGame();
            return;
        }
        // In caso non esca
        // manda un messaggio a tutti dicendo chi è il giocatore successivo
        // manda un messaggio di richiesta carte al giocatore successivo
        else{
            nextPlayer();
            for(ClientHandler c : players) {
                    //c.sendMessage(); // messaggio in cui dice chi sarà il prossimo giocatore));
                }
            }
            players.get(currentPlayer).sendMessage(new Message("server", MessageType.CHOOSE_CARDS_REQUEST));
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

    private int setFirstPlayer(int numOfPlayer){
        Random random = new Random();
        return random.nextInt(numOfPlayer);
    }
    private String[] getPlayersUsername(){
        String[] playersUsername = new String[numOfPlayers];
        for(int i=0; i<players.size(); i++){
            playersUsername[i]=players.get(i).getUsername();
        }
        return playersUsername;
    }



}
