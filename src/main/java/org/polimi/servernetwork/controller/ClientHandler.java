package org.polimi.servernetwork.controller;

import org.polimi.messages.Message;

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


    public ClientHandler(UsernameIssuer usernameIssuer, GameCodeIssuer gameCodeIssuer, LobbyController lobbyController) {
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

    public abstract void onMessage(Message message);
    public abstract void sendMessage (Message message);

    protected void disconnect () {
        System.out.println(this.username + " disconnected");

        if (lobbyController == null) {
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
            gameController.disconnection(this);
            usernameIssuer.setDisconnect(this.username);
        }
        //closes the socket and the I/O streams
        closeEverything();
    }

    protected abstract void closeEverything();


    public void setGameController(GameController gameController){
        this.gameController = gameController;
    }


}
