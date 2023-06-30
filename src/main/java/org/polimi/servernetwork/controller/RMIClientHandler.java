package org.polimi.servernetwork.controller;

import org.polimi.client.RMICallback;
import org.polimi.messages.*;
import org.polimi.servernetwork.server.Pinger;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.UnmarshalException;
import java.util.LinkedList;
import java.util.Queue;

public class RMIClientHandler extends ClientHandler{
    private Queue<Message> RMIMessages;
    private RMICallback rmistub;
    private final Object taskLock;
    private final Object RMIMessagesLock;
    private Pinger pinger;

    public RMIClientHandler(RMICallback rmistub, UsernameIssuer usernameIssuer, GameCodeIssuer gameCodeIssuer, LobbyController lobbyController) {
        super(usernameIssuer, gameCodeIssuer, lobbyController);
        this.rmistub=rmistub;
        this.RMIMessages = new LinkedList<>();
        taskLock = new Object();
        RMIMessagesLock = new Object();
        isLogged = true;
    }

    /**
     * this method waits for RMIMessageLock to be free and then enqueue the message.
     * Then it starts a new thread that waits for taskLock (happens when the previous sendMessage finished to send
     * the message) and when is free calls the method getNotified() on the rmistub.
     * @param message the message to be sent
     */
    public void sendMessage (Message message) {
        synchronized (RMIMessagesLock) {
            RMIMessages.add(message);
            System.out.println("(RMIClientHandler username " + username + ") added this message to read: " + message);
            new Thread (()-> {
                synchronized (taskLock) {
                    try {
                        rmistub.getNotified();
                    } catch (RemoteException e) {
                        System.out.println("(RMIClientHandler username: " + this.username + ") disconnection");
                        closeEverything();
                    }
                }
            }).start();
        }
    }

    public void closeEverything() {
        pinger.setConditionFalse();
    }

    public void setPinger (Pinger p) {
        this.pinger = p;
    }

    public Message popMessageRMI(){
        synchronized (RMIMessages) {
            return RMIMessages.remove();
        }
    }

    /**
     * Handles the reconnection of a client to a game session.
     * This method is called when a client attempts to reconnect to a game.
     * It associates the client handler with the corresponding game controller and updates the usernameIssuer and connection status.
     * Finally, it triggers the game controller's reconnect method to resume the game for the reconnected client.
     */

    public void reconnection(){
        int gameId = usernameIssuer.getGameID(username);
        GameController gameController = gameCodeIssuer.getGameController(gameId);
        usernameIssuer.setClientHandler(this, username);
        setGameController(gameController);
        usernameIssuer.setConnect(username);
        gameController.reconnect(this);
    }
}
