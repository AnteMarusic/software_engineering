package org.polimi.servernetwork.controller;

import org.polimi.messages.*;

/**
 * This class is responsible for handling the communication with a client.
 * la classe non è thread safe, ma è thread confined
 */
public abstract class ClientHandler{
    protected String username;
    protected UsernameIssuer usernameIssuer;
    protected GameCodeIssuer gameCodeIssuer;
    protected LobbyController lobbyController;
    protected GameController gameController;
    private boolean destruction;
    /**
     * this attribute is used to identify a socket client handler that belongs to a client
     * that hasn't successfully logged in yet. By default, is set to true on RMIClientHandlers because those are created
     * only if the client has properly logged in.
     */
    protected boolean isLogged;


    public ClientHandler(UsernameIssuer usernameIssuer, GameCodeIssuer gameCodeIssuer, LobbyController lobbyController) {
        destruction = false;
        this.usernameIssuer = usernameIssuer;
        this.gameCodeIssuer = gameCodeIssuer;
        this.lobbyController = lobbyController;
    }

    public void setUsername(String name){
        this.username = name;
    }

    public String getUsername(){
        return this.username;
    }

    public void onMessage(Message message){
        switch (message.getMessageType()){
            /*
            case USERNAME -> {

                InternalComunication internalComunication = usernameIssuer.login(message.getUsername());
                if(internalComunication == InternalComunication.OK) {
                    usernameIssuer.setClientHandler(this, message.getUsername());
                    this.username = message.getUsername();
                    sendMessage(new Message(this.username, MessageType.CHOOSE_GAME_MODE ));
                }
                if(internalComunication == InternalComunication.ALREADY_TAKEN_USERNAME) {
                    sendMessage(new ErrorMessage(this.username, ErrorType.ALREADY_TAKEN_USERNAME));
                }
                //to test
                if(internalComunication == InternalComunication.RECONNECTION){
                    this.username = message.getUsername();
                    int gameId = usernameIssuer.getGameID(message.getUsername());
                    GameController gameController = gameCodeIssuer.getGameController(gameId);
                    usernameIssuer.setClientHandler(this, message.getUsername());
                    setGameController(gameController);
                    usernameIssuer.setConnect(this.getUsername());
                    gameController.reconnect(this);
                }
            }
             */
            case CHOOSE_GAME_MODE -> {
                ChosenGameModeMessage chosenGameModeMessage = (ChosenGameModeMessage) message;
                switch (chosenGameModeMessage.getGameMode()) {
                    case JOIN_RANDOM_GAME_2_PLAYER -> lobbyController.insertPlayer(this, 2);
                    case JOIN_RANDOM_GAME_3_PLAYER -> lobbyController.insertPlayer(this, 3);
                    case JOIN_RANDOM_GAME_4_PLAYER -> lobbyController.insertPlayer(this , 4);
                    case JOIN_PRIVATE_GAME -> lobbyController.addInAPrivateGame(chosenGameModeMessage.getCode(), this);
                    case CREATE_PRIVATE_GAME -> {
                        int privateCode = gameCodeIssuer.reservCodeTo();
                        lobbyController.addPrivateGameCode(privateCode, this, chosenGameModeMessage.getNumOfPlayer());
                        sendMessage(new GameCodeMessage(privateCode));
                    }
                    default-> System.out.println("(ClientHandler) received unknown value of GameMode");
                }

            }
            case CHOSEN_CARDS_REPLY -> {
                ChosenCardsMessage chosenCards = (ChosenCardsMessage) message;
                gameController.removeCards(chosenCards.getCoordinates(), chosenCards.getCards());
                sendMessage(new Message(this.username, MessageType.CHOOSE_COLUMN_REQUEST));
            }
            case CHOSEN_COLUMN_REPLY -> {
                ChosenColumnMessage chosenColumn = (ChosenColumnMessage) message;
                gameController.insertInBookshelf(chosenColumn.getColumn());
                gameController.notifyNextPlayer();
            }
        }
    }
    public abstract void sendMessage (Message message);
    public abstract void reconnection();

    /**
     * if a ClientHandler is logged the disconnection procedure is the same between RMI and socket.
     * if the ClientHandler is not logged (this happens only in Socket) we just call closeEverything method
     */
    public void disconnect () {
        if(!destruction){
            if (isLogged) {
                System.out.println("(ClientHandler) " + this.username + " disconnected");

                if (lobbyController == null) {
                    System.out.println("(ClientHandler username: " + this.username + ") sistemic failure. for some reason lobbyController attribute is null");
                    throw new NullPointerException();
                }

                //if game controller is null you are either in a lobby or waiting to get in one
                //so, you should disconnect from it
                if (gameController == null) {
                    lobbyController.disconnect(this);
                    usernameIssuer.removeUsername(this.username);
                }
                //if you are in a game you have to be disconnected from it
                if (gameController != null) {
                    System.out.println("(ClientHandler) " + this.username + " is in a game");
                    usernameIssuer.setDisconnect(this.username);
                    gameController.disconnection(this);
                }
                //closes the socket and the I/O streams
            }
            closeEverything();
        }
        else {
            System.out.println("(ClientHandler disconnect) il game si sta distruggendo, ho notato una disconnesione di " + username + "ma non la lavoro siccome era ovvia e voluta");
        }
    }

    protected abstract void closeEverything();

    public void setGameController(GameController gameController){
        this.gameController = gameController;
    }

    public boolean gameControllerPresent(){
        return this.gameController != null;
    }

    public boolean isLogged () {
        return isLogged;
    }

    public void setDestructionTrue(){
        this.destruction = true;
    }
}
