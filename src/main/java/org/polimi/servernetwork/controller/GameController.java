package org.polimi.servernetwork.controller;

import org.polimi.messages.*;
import org.polimi.servernetwork.model.Card;
import org.polimi.servernetwork.model.Coordinates;
import org.polimi.servernetwork.model.Game;
import org.polimi.servernetwork.saver.CountDown;

import java.util.*;
import java.util.stream.Stream;

public class GameController {
    private final ArrayList<ClientHandler> players = new ArrayList<ClientHandler>();
    private static final int COUNT_DOWN = 20;
    private int gameCode;
    private final int numOfPlayers;
    private int currentPlayer;
    private final int firstPlayer;
    private Game game;
    private int countDown;
    private DecrementerGameController decrementer;
    private UsernameIssuer usernameIssuer;
    private GameCodeIssuer gameCodeIssuer;


    public GameController(ArrayList<ClientHandler> list, UsernameIssuer usernameIssuer, GameCodeIssuer gameCodeIssuer) {
        numOfPlayers = list.size();
        gameCode = 0;
        this.usernameIssuer = usernameIssuer;
        this. gameCodeIssuer = gameCodeIssuer;
        countDown = COUNT_DOWN;
        decrementer = null;
        players.addAll(list);
        firstPlayer = setFirstPlayer(numOfPlayers);
        currentPlayer = firstPlayer;
        game = new Game(numOfPlayers, firstPlayer, getPlayersUsername());
        //save the goals
    }

    public void setGameCode(int gameCode){
        this.gameCode = gameCode;
    }

    public void initGameEnv() {
        // manda a tutti clientHandler un messaggio in cui dice che il gioco sta iniziando e con chi sta giocando
        // manda a tutti la bord gli shared goal e a ogni client il proprio private goal
        List<String> usernames;
        int i;
        List<Card[][]> bookshelves = new LinkedList<>();
        usernames = game.getPlayersUsername();
        for (i = 0; i < players.size(); i ++) {
            bookshelves.add(game.getBookshelfGrid(i));
        }
        for (i = 0; i < players.size(); i++) {
            players.get(i).sendMessage(new StartGameMessage(players.get(i).getUsername(), usernames));
            players.get(i).sendMessage(new ModelStatusAllMessage(players.get(i).getUsername(), game.getBoardMap(), bookshelves, game.getIndexSharedGoal1(), game.getIndexSharedGoal2(), game.getPersonalGoalCoordinates(i),game.getPersonalGoalColors(i), game.getPersonalGoalIndex(i), usernames));
        }

    }

    public void resetGameEnv(int position){
        List<String> usernames;
        int i;
        List<Card[][]> bookshelves = new LinkedList<>();
        usernames = game.getPlayersUsername();
        for (i = 0; i < players.size(); i ++) {
            bookshelves.add(game.getBookshelfGrid(i));
        }
        players.get(position).sendMessage(new Message("server", MessageType.USERNAME));
        players.get(position).sendMessage(new ModelStatusAllMessage(players.get(position).getUsername(), game.getBoardMap(), bookshelves, game.getIndexSharedGoal1(), game.getIndexSharedGoal2(), game.getPersonalGoalCoordinates(position),game.getPersonalGoalColors(position), game.getPersonalGoalIndex(position), usernames));
    }
    //questo metodo non viene chiamato nel caso di client rmi
    public void startGameTurn() {
        System.out.println("(GameController startGameTurn)");
        // comunica al primo giocatore d'iniziare scegliendo le carte da rimuovere dalla board
        /*
        new Thread(() -> {

            players.get(currentPlayer).sendMessage(new Message("server", MessageType.CHOOSE_CARDS_REQUEST));
        }).start();

         */
        players.get(currentPlayer).sendMessage(new Message("server", MessageType.CHOOSE_CARDS_REQUEST));
        for (ClientHandler d : players) {
            System.out.println("entrato nel for");
            if (d != players.get(currentPlayer) && d != null) {
                System.out.println("entrato nel if");
                d.sendMessage(new NotifyNextPlayerMessage(d.getUsername(), players.get(currentPlayer).getUsername()));
            }
        }
    }

    /**
     * removes the card from the board, stores them in game for bookshelf insertion.
     * sends a message to all the players containing the coordinates of the cards to remove and the coordinates
     * of the card to update to pickable
     *
     * @param coordinates coordinates to remove (sent by the client)
     */
    public void removeCards(List<Coordinates> coordinates) {

        boolean empty = game.remove(coordinates);
        //save status
        for (ClientHandler c : players) {
            if (c != null && !c.equals(players.get(currentPlayer))) {
                c.sendMessage(new CardToRemoveMessage("server", coordinates));
            }
        }
        if(empty) {
            System.out.println("la board è vuota, la riempio");
            game.fillBoard();
            //save staus
            for (ClientHandler c : players) {
                if (c != null) {
                    c.sendMessage(new BoardMessage(game.getBoardMap()));
                }
            }
        }
    }

    public void insertInBookshelf(int column) {
        int currentPoints = game.insertInBookshelf(column, currentPlayer);
        //save status
        // manda al giocatore corrente il punteggio attuale
        players.get(currentPlayer).sendMessage(new CurrentScore("server", currentPoints));
        for (ClientHandler c : players) {
            if (c!=null && !c.equals(players.get(currentPlayer))) {
                c.sendMessage(new ChosenColumnMessage("server", column));
            }
        }
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
            System.out.println("current player is: " + currentPlayer);
            nextPlayer();
            System.out.println("next player is: " + currentPlayer);
            // faccio un controllo: se il giocatore successivo è l'unico presente non lo faccio giocare
            if(getNumOfConnectedPlayers()==1){
                // gli comunico solamente che è rimasto da solo e parte il timer di 60 secondi
                players.get(currentPlayer).sendMessage(new Message("server", MessageType.AREALONE));
                decrementer = new DecrementerGameController(this);
                new Thread (decrementer).start();
                // se non succede niente entro i 60 secondi il timer scaduto si occuperà di comunicare al giocatore di aver vinto il gioco
                // se invece nel mentre un giocatore dovesse entrare, per come è scritta la reconnection il timer verrà stoppato e inizierà il turno per il giocatore dentro da più tempo
                return;
            }
            // se invece ci sono almeno due giocatori collegati:
            // comunico a tutti chi sarà il prossimo giocatore
            for (ClientHandler c : players) {
                if (c != players.get(currentPlayer) && c!=null)
                    c.sendMessage(new NotifyNextPlayerMessage("server", players.get(currentPlayer).getUsername())); // messaggio in cui dice chi sarà il prossimo giocatore));
            }
            // mando al prossimo giocatore la card request
            players.get(currentPlayer).sendMessage(new Message("server", MessageType.CHOOSE_CARDS_REQUEST));
        }
    }

    private void endGame(){
        Map<String,Integer> gameRanking = game.endGame();
        gameAwarding(gameRanking);
        System.out.println("il gioco è finito");
        // fermo il thred 10 secodni per dare il tempo ai client di leggersi i messaggi e poi elimino tutti i client handler
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){
            System.out.println("eccezione lanciata in GameController nel metodo end game");
        }

        closeGame();
    }

    private void gameAwarding (Map<String,Integer> ranking){
        // mando a tutti un messaggio contenente la classifica
        for(ClientHandler player : players ){
            if (player != null) {
                player.sendMessage(new RankingMessage("server", ranking));
            }
        }
    }
    private void nextPlayer() {
        do {
            currentPlayer = (currentPlayer + 1) % numOfPlayers;
        }while (players.get(currentPlayer)==null);
    }

    public void reconnect (ClientHandler clientHandler){
        System.out.println(clientHandler.getUsername() + " sta cercando di riconnettersi");
        // riinserisco il clienthandler nella lista di clienthanler del gameController
        int position = game.getPosition(clientHandler.getUsername());
        System.out.println(clientHandler.getUsername() + " era in posizione " + position);
        System.out.println("questa è la lista di clienthandler: " + players.toString());
        players.set(position, clientHandler);
        System.out.println("questa è la lista di clienthandler: " + players.toString());
        resetGameEnv(position);
        for(ClientHandler c : players ) {
            if(c !=null && c != clientHandler)
                c.sendMessage(new ReconnectionMessage("server", clientHandler.getUsername()));
        }
        clientHandler.sendMessage(new Message("server", MessageType.WAITING_FOR_YOUR_TURN));
        // faccio un controllo: se adesso il numero di giocatori connsessi è 2 significa che prima che io mi riconnettessi il giocatore era dentro da solo
        // quindi non stava giocando ma era solo in attesa
        if(getNumOfConnectedPlayers()==2) {
            // in questo caso azzero il timer di decrementer
            // non è detto ch il decrementer sia partito
            if (countDown != COUNT_DOWN) {
                decrementer.stop();
                System.out.println("counter riportato a 60 e fermato per via di una riconnessione");

                // riporto coutDown a 60
                countDown = COUNT_DOWN;
                //e assegno il turno al giocatore che era già dentro al gioco (teoricamente dovrebbe essere già il currentplayer)
                // gli chiedo di scegliere le carte da inserire
                players.get(currentPlayer).sendMessage(new Message("server", MessageType.CHOOSE_CARDS_REQUEST));
            }
        }
    }
    public void disconnection(ClientHandler clientHandler){
        System.out.println("(GameController) disconnect " + clientHandler.getUsername());
        String username = clientHandler.getUsername();
        // setto nella lista di clientHandler il client che è uscito a null
        System.out.println("questa è la lista di clienthandler: " + players.toString());
        players.set(players.indexOf(clientHandler), null);
        if(getNumOfConnectedPlayers()==0){
            if (countDown != COUNT_DOWN)
                decrementer.stop();
            closeGame();
        }
        else if(players.get(currentPlayer) == null){   // se il giocatore che si è disconnesso è il currentPlayer
            if(getNumOfConnectedPlayers()==1){      // non ho messo questa condizione fuori dal if sopra siccome se chi esce non è il current player per correttezza voglio lasciare finire il currentplayer
                                                    // e quando ha finito il turno in caso gli dico di essere rimasto da solo

                decrementer = new DecrementerGameController(this);
                new Thread (decrementer).start();

                //passo il turno al successivo (l'unico giocatore rimasto)
                nextPlayer();
                // ma non lo faccio giocare, gli comunico di essere rimasto da solo
                players.get(currentPlayer).sendMessage(new Message("server", MessageType.AREALONE));
                // ora ho finito e vado in attesa di messaggi
                // intanto parte il timer di 60 secondi, se nessuno si riconnette in questi 60 secondi il gioco finisce e il giocatore rimasto è il vincitore
                // come mi accorgo se qualcuno si è riconnesso? nella reconnection controllo se il giocatore era rimasto da solo, se era rimasto da solo riprendo il gioco
                // come?
                // quando un si riconnette e c'era un solo giocatore fin'ora nella reconnection azzero il timer e passo il tunro all'unico giocatore che c'era già
                // così riprende il flusso di gioco
            }
            else {
                // entro qui se il giocatore disconneso era il giocatore corrente, e con lui fuori siamo ancora in almeno in due
                nextPlayer();
                // comunico ai gicatori chi è uscito e
                // comunico ai giocatori chi è il giocatore successivi (tranne al giocatore successivo che se ne accorgerà)
                for (ClientHandler c : players) {
                    if (c != players.get(currentPlayer) && c!=null) {
                        c.sendMessage(new DisconnectionAlert("server", username));
                        c.sendMessage(new NotifyNextPlayerMessage("server", players.get(currentPlayer).getUsername())); // messaggio in cui dice chi sarà il prossimo giocatore));
                    }
                }
                // comunico al giocatore successivo di giocare
                players.get(currentPlayer).sendMessage(new Message("server", MessageType.CHOOSE_CARDS_REQUEST));
            }
        }
        else{   // entro qui se il giocatore disconnesso non era il currentPlayer e se c'è almeno un giocatore dentro
            // non gestisco qui la situazione se è rimasto un solo giocatore, la devo gestire nella funzione che comunica al giocatore successivo di giocare (nel ciclo fi gioco principale), non giocherà se è rimasto da solo
            // non posso gestirla qui siccome il client che entra cui se non è il currentplayer non è il nostro "thread principale" se mandasse messaggi creerebbe casino

            //comunico a tutti i giocatori che clieentHandler.getUsername è uscito
            for (ClientHandler c : players) {
                if (c!=null)
                    c.sendMessage(new DisconnectionAlert("server", username));
            }
        }
    }

    private int setFirstPlayer(int numOfPlayer){
        Random random = new Random();
        return random.nextInt(numOfPlayer);
    }
    private List<String> getPlayersUsername(){
        List<String> playersUsername = new ArrayList<>(numOfPlayers);
        for(int i=0; i<players.size(); i++){
            playersUsername.add(i,players.get(i).getUsername());
        }
        return playersUsername;
    }

    public ClientHandler getClienthandlerfromGC(String name){
        Optional<ClientHandler> clienthandler = Stream.of(players)
                .flatMap(ArrayList::stream)
                .filter(clientHandler -> clientHandler.getUsername().equals(name))
                .findFirst();
        return clienthandler.orElse(null);
    }

    private int getNumOfConnectedPlayers(){
        int counter=0;
        for(int i=0; i<numOfPlayers;i++){
            if(players.get(i)!=null){
                counter++;
            }
        }
        return counter;
    }

    private void closeGame(){
        /*
        toglie il game da gameIdIssuer, libera i nomi da usernameIssuer
        distruggo tutte le strutture create
        */
        List<String> playersUsername = game.getPlayersUsername();
        for(int i=0; i<numOfPlayers; i++){
            usernameIssuer.removeUsername(playersUsername.get(i));
        }
        gameCodeIssuer.removeGame(gameCode);
        for (ClientHandler c : players) {
            if (c!=null)
                c.closeEverything();
        }
        players.clear();
        System.out.println("chiuso il gioco " + gameCode);
        game = null;
    }

    public void decreaseCountDown () {
        countDown--;
        if(countDown==COUNT_DOWN - 1){
            System.out.println("count down started");
        }
        if (countDown == 0) {
            System.out.println("sono dentro al metodo decreaseCountDown e countDown è uguale a 0");
            // fermo il Decrementer
            decrementer.stop();
            // decreto il vincitore
            // mando messaggio di fine partita
            // chiudo il gioco
            endGame();
        }
    }
    public ArrayList<ClientHandler> returnClientHandlers(){
        return players;
    }
}
