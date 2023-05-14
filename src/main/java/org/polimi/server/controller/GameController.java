package org.polimi.server.controller;

import org.polimi.messages.*;
import org.polimi.server.model.Card;
import org.polimi.server.model.Coordinates;
import org.polimi.server.model.Game;

import java.util.*;

public class GameController {


    private final ArrayList<ClientHandler> players = new ArrayList<ClientHandler>();
    private final int numOfPlayers;
    private int currentPlayer;
    private final int firstPlayer;
    private final Game game;


    public GameController(ArrayList<ClientHandler> list) {
        numOfPlayers = list.size();
        players.addAll(list);
        firstPlayer = setFirstPlayer(numOfPlayers);
        currentPlayer = firstPlayer;
        game = new Game(numOfPlayers, firstPlayer, getPlayersUsername());
        initGameEnv();
        startGameTurn();

    }

    private void initGameEnv() {
        // manda a tutti clientHandler un messaggio in cui dice che il gioco sta iniziando e con chi sta giocando
        // manda a tutti la bord gli shared goal e a ogni client il proprio private goal
        String[] usernames = new String[numOfPlayers];
        int i;
        List<Card[][]> bookshelves = new LinkedList<>();
        usernames = getPlayersUsername();
        for (i = 0; i < players.size(); i ++) {
            bookshelves.add(game.getBookshelfGrid(i));
        }
        for (i = 0; i < players.size(); i++) {
            players.get(i).sendMessage(new StartGameMessage("server", usernames));
            players.get(i).sendMessage(new ModelStatusAllMessage("server", game.getBoardMap(), bookshelves, game.getIndexSharedGoal1(), game.getIndexSharedGoal2(), game.getPersonalGoalIndex(i), usernames));
        }

    }

    private void startGameTurn() {
        players.get(currentPlayer).sendMessage(new Message("server", MessageType.CHOOSE_CARDS_REQUEST));
        // comunica al primo giocatore d'iniziare scegliendo le carte da rimuovere dalla board
    }

    /**
     * removes the card from the board, stores them in game for bookshelf insertion.
     * sends a message to all the players containing the coordinates of the cards to remove and the coordinates
     * of the card to update to pickable
     *
     * @param coordinates coordinates to remove (sent by the client)
     */
    public void removeCards(List<Coordinates> coordinates) {
        game.remove(coordinates);
        for (ClientHandler c : players) {
            c.sendMessage(new CardToRemove("server", coordinates, game.geToUpdateToPickable()));
        }
    }

    public void insertInBookshelf(int column) {
        int currentPoints = game.insertInBookshelf(column, currentPlayer);
        // manda al giocatore corrente il punteggio attuale
        players.get(currentPoints).sendMessage(new CurrentScore("server", currentPoints));
    }


    /**
     *if endGame is true and next player is the first one calls endGameMethod. Otherwise updates the
     * currentPlayer, sends choose_card_message to the new current player and update_next_player_message
     * to the other players.
     */
    public void notifyNextPlayer() {
        // se Game.endGame è true e il prossimo giocatore è il firstPlayer
        // chiama il metodo endGame ed esce da questo metodo

        if (game.getEndGame() && (currentPlayer + 1) % numOfPlayers == firstPlayer) {
            endGame();
        }
        // In caso non esca
        // manda un messaggio a tutti dicendo chi è il giocatore successivo
        // manda un messaggio di richiesta carte al giocatore successivo
        else {
            nextPlayer();
            for (ClientHandler c : players) {
                if (c != players.get(currentPlayer) && c!=null)
                    c.sendMessage(new NotifyNextPlayerMessage("server", players.get(currentPlayer).getUsername())); // messaggio in cui dice chi sarà il prossimo giocatore));

            }
            players.get(currentPlayer).sendMessage(new Message("server", MessageType.CHOOSE_CARDS_REQUEST));
        }
    }

    private void endGame(){
        Map<String,Integer> gameRanking = game.endGame();
        gameAwarding(gameRanking);
    }

    private void gameAwarding (Map<String,Integer> ranking){
        // mando a tutti un messaggio contenente la classifica
        for(ClientHandler player : players ){
            player.sendMessage(new RankingMessage("server", ranking));
        }
    }
    private void nextPlayer() {
        do {
            currentPlayer = (currentPlayer + 1) % numOfPlayers;
        }while (players.get(currentPlayer)==null);
    }

    public void reconnect (ClientHandler clientHandler){
        // riinserisco il clienthandler nella lista di clienthanler del gameController
        int position = game.getPosition(clientHandler.getUsername());
        players.add(position, clientHandler);
        for(ClientHandler c : players ) {
            if(c != clientHandler)
                c.sendMessage(new ReconnectionMessage("server", clientHandler.getUsername()));
        }
        clientHandler.sendMessage(new Message("server", MessageType.WAITING_FOR_YOUR_TURN));
    }
    public void disconnection(ClientHandler clientHandler){
        if(clientHandler == players.get(currentPlayer)){
            nextPlayer();
            //comunico a tutti i giocatori che clieentHandler.getUsername è uscito
            // ...
            // comunico ai giocatori chi è il giocatore successivi
            for (ClientHandler c : players) {
                if (c != players.get(currentPlayer) && c!=null)
                    c.sendMessage(new NotifyNextPlayerMessage("server", players.get(currentPlayer).getUsername())); // messaggio in cui dice chi sarà il prossimo giocatore));

            }
            // comunico al giocatore successivo di giocare
            players.get(currentPlayer).sendMessage(new Message("server", MessageType.CHOOSE_CARDS_REQUEST));

        }
        // setto nella lista di clientHandler il client che è uscito a null
        players.set(players.indexOf(clientHandler), null);
        // devo settare in usernameIssuer lo username con lo stato disconnected


    }

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
