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

    public RMIClientHandler(RMICallback rmistub, UsernameIssuer usernameIssuer, GameCodeIssuer gameCodeIssuer, LobbyController lobbyController) {
        super(usernameIssuer, gameCodeIssuer, lobbyController);
        this.rmistub=rmistub;
        this.RMIMessages = new LinkedList<>();
    }
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
    public void sendMessage (Message message) {
        new Thread (() -> {
                synchronized (RMIMessages) {
                    try {
                        RMIMessages.add(message);
                        System.out.println("(RMICLIentHandler) aggiunto questo messaggio da leggere per " + username + ": " + message);
                        rmistub.getNotified();
                    } catch (UnmarshalException e) {
                        System.out.println("Client disconnected");
                        closeEverything();
                    } catch (RemoteException e) {
                        System.out.println("Client disconnected");
                        closeEverything();
                    }
                    catch (IOException IOe) {
                        System.out.println("Client disconnected");
                        closeEverything();
                    }
                }
        }).start();
        }

    public void closeEverything() {
    }

    public Message popMessageRMI(){
        return RMIMessages.remove();
    }
}
