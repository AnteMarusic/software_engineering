package org.polimi.servernetwork.controller;

import org.polimi.client.RMICallback;
import org.polimi.messages.*;

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

    public RMIClientHandler(RMICallback rmistub, UsernameIssuer usernameIssuer, GameCodeIssuer gameCodeIssuer, LobbyController lobbyController) {
        super(usernameIssuer, gameCodeIssuer, lobbyController);
        this.rmistub=rmistub;
        this.RMIMessages = new LinkedList<>();
        taskLock = new Object();
        RMIMessagesLock = new Object();
        isLogged = true;
    }
    /*
    public void onMessage(Message message){
        switch (message.getMessageType()){

            case USERNAME -> {
                this.username = message.getUsername();
                sendMessage(new Message(this.username, MessageType.CHOOSE_GAME_MODE ));

            }

            case CHOOSE_GAME_MODE -> {
                ChosenGameModeMessage chosenGameModeMessage = (ChosenGameModeMessage) message;
                switch (chosenGameModeMessage.getGameMode()) {
                    case JOIN_RANDOM_GAME_2_PLAYER -> lobbyController.insertPlayer(this, 2);
                    case JOIN_RANDOM_GAME_3_PLAYER -> lobbyController.insertPlayer(this, 3);
                    case JOIN_RANDOM_GAME_4_PLAYER -> lobbyController.insertPlayer(this , 4);
                    case JOIN_PRIVATE_GAME -> lobbyController.addInAPrivateGame(chosenGameModeMessage.getCode(), this);
                    case CREATE_PRIVATE_GAME -> {
                        if(gameCodeIssuer.alreadyExistGameCode(chosenGameModeMessage.getCode()) || lobbyController.readyToCreatePrivateGame(chosenGameModeMessage.getCode())){
                            sendMessage(new Message(this.username, MessageType.ALREADYTAKENGAMECODEMESSAGE ));
                            sendMessage(new Message(this.username, MessageType.CHOOSE_GAME_MODE ));
                        }
                        else{
                            lobbyController.addPrivateGameCode(chosenGameModeMessage.getCode(), this, chosenGameModeMessage.getNumOfPlayer());
                        }
                    }
                    default-> System.out.println("errore nella ricezione del messaggio gamemode");
                }

            }
            case CHOSEN_CARDS_REPLY -> {
                ChosenCardsMessage chosenCards = (ChosenCardsMessage) message;
                gameController.removeCards(chosenCards.getCoordinates());
                sendMessage(new Message(this.username, MessageType.CHOOSE_COLUMN_REQUEST));
            }
            case CHOSEN_COLUMN_REPLY -> {
                ChosenColumnMessage chosenColumn = (ChosenColumnMessage) message;
                gameController.insertInBookshelf(chosenColumn.getColumn());
                gameController.notifyNextPlayer();
            }
        }
    }
    */

    /**
     * this method waits for RMIMessageLock to be free and then enqueue the message.
     * Then it starts a new thread that waits for taskLock (happens when the previous sendMessage finished to send
     * the message) and when is free calls the method getNotified() on the rmistub.
     * @param message the message to be sent
     */
    public void sendMessage (Message message) {
        synchronized (RMIMessagesLock) {
            RMIMessages.add(message);
            System.out.println("(RMIClientHandler) added this message for " + username + " to read: " + message);
            new Thread (()-> {
                synchronized (taskLock) {
                    try {
                        rmistub.getNotified();
                    } catch (UnmarshalException e) {
                        System.out.println("(RMIClientHandler username: " + this.username + ") disconnection");
                        closeEverything();
                    } catch (RemoteException e) {
                        System.out.println("(RMIClientHandler username: " + this.username + ") disconnection");
                        closeEverything();
                    }
                    catch (IOException IOe) {
                        System.out.println("(RMIClientHandler username: " + this.username + ") disconnection");
                        closeEverything();
                    }
                }
            }).start();
        }
    }

    public void closeEverything() {
    }

    public Message popMessageRMI(){
        synchronized (RMIMessages) {
            return RMIMessages.remove();
        }
    }

    public void reconnection(){
        int gameId = usernameIssuer.getGameID(username);
        GameController gameController = gameCodeIssuer.getGameController(gameId);
        usernameIssuer.setClientHandler(this, username);
        setGameController(gameController);
        usernameIssuer.setConnect(username);
        gameController.reconnect(this);
    }
}
