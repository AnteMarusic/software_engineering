package org.polimi.servernetwork.controller;

import org.polimi.messages.*;
import org.polimi.servernetwork.model.Card;
import org.polimi.servernetwork.model.Coordinates;
import org.polimi.servernetwork.model.Game;

import java.io.*;
import java.util.*;
import java.util.stream.Stream;

public class GameController {
    private boolean destruction;
    private final ArrayList<ClientHandler> players = new ArrayList<ClientHandler>();
    private static final int COUNT_DOWN = 20;
    private int gameCode;
    private int numOfPlayers;
    private int currentPlayer;
    private int firstPlayer;
    private Game game;
    private int countDown;
    private DecrementerGameController decrementer;
    private UsernameIssuer usernameIssuer;
    private GameCodeIssuer gameCodeIssuer;
    private File saveFile;
    private static String path = "/src/main/resources/ser/";


    public GameController(ArrayList<ClientHandler> list, UsernameIssuer usernameIssuer, GameCodeIssuer gameCodeIssuer) {
        destruction = false;
        numOfPlayers = list.size();
        gameCode = 0; //temporarily set to zero. It will be set to a different value later on
        this.usernameIssuer = usernameIssuer;
        this. gameCodeIssuer = gameCodeIssuer;
        countDown = COUNT_DOWN;
        decrementer = null;
        players.addAll(list);
        firstPlayer = setFirstPlayer(numOfPlayers);
        currentPlayer = firstPlayer;
        game = new Game(numOfPlayers, firstPlayer, getPlayersUsername());
    }

    /**
     * gameController constructor used in case of server disconnection
     * @param usernameIssuer
     * @param gameCodeIssuer
     * @param gameCode
     */
    public GameController(UsernameIssuer usernameIssuer, GameCodeIssuer gameCodeIssuer, int gameCode) {
        this.usernameIssuer = usernameIssuer;
        this.gameCodeIssuer = gameCodeIssuer;
        this.gameCode = gameCode;
        firstPlayer = -1;
        numOfPlayers = -1;
    }

    public void retrieveGameFromFile () throws FileNotFoundException {
        try {
            String filePath = new File("").getAbsolutePath();
            this.saveFile = new File(filePath.concat(path + gameCode + ".ser"));
            this.game = readFileAndDeserialize();
            countDown = COUNT_DOWN;
            decrementer = null;
            firstPlayer = game.getFirstPlayer();
            numOfPlayers = game.getNumOfPlayers();
            //setto a null tutti i client handler in modo che funzioni il metodo numOfConnectedPlayers
            for (int i=0; i < numOfPlayers; i ++) {
                players.add(null);
            }
            System.out.println("(GameController retrieveGameFromFile) game successfully retrieved");
        } catch (RuntimeException e) {
            System.out.println("(GameController retrieveGameFromFile) error in game retrieving");
            throw new FileNotFoundException();
        }
    }

    public void initializeSaveFile() {
        //save
        String filePath = new File("").getAbsolutePath();
        System.out.println("(GameController initializeSaveFile) model status will be saved here: " + filePath.concat(path + gameCode + ".ser"));
        this.saveFile = new File(filePath.concat(path + gameCode + ".ser"));
        try {
            this.saveFile.createNewFile();
        }catch(IOException e) {
            System.out.println("(GameController initializeSaveFile) exception in file creation");
        }
        System.out.println("(GameController initializeSaveFile) registering gameCode in gameListFile");
        GameListFileAccessorSingleton fileAccessor = GameListFileAccessorSingleton.getInstance();
        List <String> usernames = new LinkedList<>();
        players.forEach((clientHandler) -> usernames.add(clientHandler.getUsername()));
        fileAccessor.addGameIdWithPlayers(this.gameCode, usernames);
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
            players.get(i).sendMessage(new ModelStatusAllMessage(players.get(i).getUsername(),currentPlayer, game.getBoardMap(), bookshelves, game.getIndexSharedGoal1(), game.getIndexSharedGoal2(), game.getPersonalGoalCoordinates(i),game.getPersonalGoalColors(i), game.getPersonalGoalIndex(i), usernames));
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
        players.get(position).sendMessage(new ModelStatusAllMessage(players.get(position).getUsername(),currentPlayer, game.getBoardMap(), bookshelves, game.getIndexSharedGoal1(), game.getIndexSharedGoal2(), game.getPersonalGoalCoordinates(position),game.getPersonalGoalColors(position), game.getPersonalGoalIndex(position), usernames));
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
            if (d != players.get(currentPlayer) && d != null) {
                System.out.println("(GameController startGameTurn) sending NotifyNextPlayerMessage to " + players.get(currentPlayer).getUsername());
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
    public void removeCards(List<Coordinates> coordinates, List<Card> cards) {

        boolean empty = game.remove(coordinates);
        //save status
        this.save();
        for (ClientHandler c : players) {
            //ho rimosso dall'if la condizione per la quale il messaggio non lo inviava a quello che ha effettivamente rimosso le carte
            if (c != null) {
                c.sendMessage(new CardToRemoveMessage("server", coordinates, cards));
            }
        }
        if(empty) {
            System.out.println("(GameController removeCards) board is empty, refilling it");
            game.fillBoard();
            //save staus
            this.save();
            for (ClientHandler c : players) {
                if (c != null) {
                    System.out.println("(GameController removeCards) " + BoardMessage.mapToString(game.getBoardMap()));
                    c.sendMessage(new BoardMessage(game.getBoardMap()));
                }
            }
        }
    }

    public void insertInBookshelf(int column) {
        boolean before1 = game.getAchievementOfSG1(currentPlayer);
        boolean before2 = game.getAchievementOfSG2(currentPlayer);
        int currentPoints = game.insertInBookshelf(column, currentPlayer);
        // vuol dire che ho completato lo shared goal 1 in questo turno
        if(before1 != game.getAchievementOfSG1(currentPlayer)) {
            int newPoints = game.getSharedScore1(currentPlayer);
            for (ClientHandler c : players) {
                c.sendMessage(new SharedScoreAchieveMessage(1, newPoints));
            }
        }
        if(before2 != game.getAchievementOfSG2(currentPlayer)){
            int newPoints = game.getSharedScore2(currentPlayer);
            for (ClientHandler c : players) {
                c.sendMessage(new SharedScoreAchieveMessage(2, newPoints));
            }
        }
        //save status
        this.save();
        // manda al giocatore corrente il punteggio attuale
        players.get(currentPlayer).sendMessage(new CurrentScore("server", currentPoints));
        for (ClientHandler c : players) {

            if (c!=null && !c.equals(players.get(currentPlayer))) {
                c.sendMessage(new ChosenColumnMessage("server", column));
            }
            /*
            // mando a tutti il punto più in alto nella queue dei punti shared
            if(c!=null){
                c.sendMessage(new SharedPointQueueTOPMessage("serve", game.getSharedPointQueueTOP()));
            }

             */
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
            System.out.println("(GameController notifyNextPlayer) current player is: " + currentPlayer);
            nextPlayer();
            System.out.println("(GameController notifyNextPlayer) next player is: " + currentPlayer);
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
        System.out.println("(GameController endGame) gameEnded");
        // fermo il thred 10 secodni per dare il tempo ai client di leggersi i messaggi e poi elimino tutti i client handler
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){
            System.out.println("(GameController endGame) exception");
            e.printStackTrace();
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

    /**
     * TODO vedi commento sotto
     * @param clientHandler
     */
    public void reconnect (ClientHandler clientHandler){
        System.out.println("(GameController reconnect) " + clientHandler.getUsername() + " is trying to reconnect");
        // reinserisco il client handler nella lista di chanticleer del gameController
        int position = game.getPosition(clientHandler.getUsername());
        System.out.println("(GameController reconnect) " + clientHandler.getUsername() + " was in position " + position);
        System.out.println("(GameController reconnect) this is the clientHandler list before the insert of the new player: " + players.toString());
        players.set(position, clientHandler);
        System.out.println("(GameController reconnect) this is the clientHandler list after the insert of the new player: " + players.toString());
        resetGameEnv(position);
        for(ClientHandler c : players ) {
            if(c !=null && c != clientHandler)
                c.sendMessage(new ReconnectionMessage("server", clientHandler.getUsername()));
        }
        clientHandler.sendMessage(new Message("server", MessageType.WAITING_FOR_YOUR_TURN));
        // faccio un controllo: se adesso il numero di giocatori connsessi è 2 significa che prima che io mi riconnettessi il giocatore era dentro da solo
        // quindi non stava giocando ma era solo in attesa

        //per la riconnessione in caso di caduta del server ci vuole un if getNumOfConnectedPlayers() == 1
        //in questo caso facciamo partire un count down in modo che se si riconnette solo un tizio il gioco non
        //rimarrà per sempre aperto ma si chiuderà. Magari il countDown deve essere diverso da quello di fine gioco?
        if (getNumOfConnectedPlayers() == 1) {
            decrementer = new DecrementerGameController(this);
            new Thread (decrementer).start();
            this.currentPlayer = game.getPosition(clientHandler.getUsername());
            players.get(currentPlayer).sendMessage(new Message("server", MessageType.CHOOSE_CARDS_REQUEST));
        }

        if(getNumOfConnectedPlayers()==2) {
            // in questo caso azzero il timer di decrementer
            // non è detto ch il decrementer sia partito
            if (countDown != COUNT_DOWN) {
                decrementer.stop();
                System.out.println("(GameController reconnect) someone reconnected. Counter is brought back to " + COUNT_DOWN);

                // riporto coutDown a 60
                countDown = COUNT_DOWN;
                //e assegno il turno al giocatore che era già dentro al gioco (teoricamente dovrebbe essere già il currentplayer)
                // gli chiedo di scegliere le carte da inserire
                players.get(currentPlayer).sendMessage(new Message("server", MessageType.CHOOSE_CARDS_REQUEST));
            }
        }
    }
    public void disconnection(ClientHandler clientHandler){
        // se la distrucction è a TRUE non devo gestire le disconnesioni, siccome il gioco si è chiuso
        if(!destruction){
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
        else {
            System.out.println("(Game Controller disconnect) il game si sta distruggendo, ho notato una disconnesione del giocatore" + clientHandler.getUsername() + "ma non la lavoro siccome era ovvia e voluta");
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

    private void closeGame() {
        /*
        toglie il game da gameIdIssuer, libera i nomi da usernameIssuer
        distruggo tutte le strutture create
        */
        destruction = true;
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
        System.out.println("(GameController closeGame) closed game with id: " + gameCode);
        game = null; //sets reference to null so game can be collected by the garbage collector
        //delete save file and remove game and usernames from gameList file
        closeSaveFile();
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

    public void save () {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(saveFile, false))) {
            oos.writeObject(game);
            System.out.println("(GameController save) game object serialized and saved");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("(GameController save) exception in file writing");
        }
    }

    public Game readFileAndDeserialize () throws RuntimeException{
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(saveFile))) {
            Game deserializedGame = (Game) ois.readObject();
            System.out.print("(GameController readFileAndDeserialize) game object deserialized");
            System.out.println(deserializedGame);
            return deserializedGame;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("(GameController readFileAndDeserialize) exception in file reading");
            throw new RuntimeException();
        }
    }

    private void closeSaveFile () {
        if (saveFile.exists()) {
            boolean deleted = saveFile.delete();
            if (deleted) {
                System.out.println("(GameController closeSaveFile) File deleted successfully");
            } else {
                System.out.println("(GameController closeSaveFile) Failed to delete the file.");
            }
        } else {
            System.out.println("(GameController closeSaveFile) File does not exist");
        }
        GameListFileAccessorSingleton fileAccessor = GameListFileAccessorSingleton.getInstance();
        fileAccessor.removeGameIdWithPlayers(this.gameCode);
    }

    public boolean getDestruction(){
        return destruction;
    }
}
