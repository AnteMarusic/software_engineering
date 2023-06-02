package org.polimi.servernetwork.controller;

import org.polimi.messages.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class ClientHandler implements Runnable{
    private static final int COUNTDOWN = 5;
    private Socket socket;
    private boolean rmi;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private String username;
    private UsernameIssuer usernameIssuer;
    private GameCodeIssuer gameCodeIssuer;
    private LobbyController lobbyController;
    private GameController gameController;
    private Queue<Message> RMIMessages;
    private int countDown;

    public ClientHandler(boolean rmi,Socket socket, UsernameIssuer usernameIssuer, GameCodeIssuer gameCodeIssuer, LobbyController lobbyController) {
        try {
            this.rmi = rmi;
            this.countDown = COUNTDOWN;
            this.usernameIssuer = usernameIssuer;
            this.gameCodeIssuer = gameCodeIssuer;
            this.lobbyController = lobbyController;
            this.socket = socket;
            if(!rmi){
                input = new ObjectInputStream(socket.getInputStream());
                output = new ObjectOutputStream(socket.getOutputStream());
            }
            else{
                this.RMIMessages = new LinkedList<>();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void resetCountDown () {
        this.countDown = COUNTDOWN;
    }

    public void decreaseCountDown () {
        this.countDown--;
        if (this.countDown == 0) {
            this.disconnect();
        }
    }

    public void setUsername(String name){
        this.username = name;
    }

    public String getUsername(){
        return this.username;
    }

    @Override
    public void run() {
        Message messageFromClient = null;
        while (!rmi && socket != null && socket.isConnected()) {
            try {
                messageFromClient = (Message) input.readObject();
                // posso scartare tutti i messaggi di un client che è gia connesso alla partita se non è il suo turno

                if (messageFromClient != null) {
                    System.out.println(messageFromClient);
                    onMessage(messageFromClient);
                } else {
                    //message == null. we suppose that this happens because you disconnected
                    System.out.println("received a null message from client " + username);
                    disconnect();
                }
            } catch (IOException e) {
                System.out.println("client " + username + " disconnected");
                disconnect();
                closeEverything();

            } catch (ClassNotFoundException e) {
                System.out.println("exception class not found in ClientHandler run, " +
                        "we disconnected the client " + username + "that sent the message");
                disconnect();
            }
        }
        if (socket != null) {
            System.out.println("client " + username + "disconnected");
            disconnect();
        }

    }
    public void onMessage(Message message){
        switch (message.getMessageType()){
            case USERNAME -> {
                InternalComunication internalComunication = usernameIssuer.login(message.getUsername());
                if(internalComunication == InternalComunication.OK) {
                    usernameIssuer.setClientHandler(this, message.getUsername());
                    this.username = message.getUsername();
                    sendMessage(new Message("server", MessageType.CHOOSE_GAME_MODE ));
                }
                if(internalComunication == InternalComunication.ALREADY_TAKEN_USERNAME) {
                    sendMessage(new ErrorMessage("server", ErrorType.ALREADY_TAKEN_USERNAME));
                }
                //to test
                if(internalComunication == InternalComunication.RECONNECTION){
                    this.username = message.getUsername();
                    int gameId = usernameIssuer.getGameID(message.getUsername());
                    GameController gameController = gameCodeIssuer.getGameController(gameId);
                    usernameIssuer.setConnect(this.getUsername());
                    gameController.reconnect(this);
                }
            }
            case CHOOSE_GAME_MODE -> {
                ChosenGameModeMessage chosenGameModeMessage = (ChosenGameModeMessage) message;
                switch (chosenGameModeMessage.getGameMode()) {
                    case JOIN_RANDOM_GAME_2_PLAYER -> {
                        lobbyController.insertPlayer(this, 2);
                    }
                    case JOIN_RANDOM_GAME_3_PLAYER -> {
                        lobbyController.insertPlayer(this, 3);
                    }
                    case JOIN_RANDOM_GAME_4_PLAYER -> {
                        lobbyController.insertPlayer(this , 4);
                    }
                    case JOIN_PRIVATE_GAME -> {
                        lobbyController.addInAPrivateGame(chosenGameModeMessage.getCode(), this);
                    }
                    case CREATE_PRIVATE_GAME -> {
                        if(gameCodeIssuer.alreadyExistGameCode(chosenGameModeMessage.getCode()) || lobbyController.readyToCreatePrivateGame(chosenGameModeMessage.getCode())){
                            sendMessage(new Message("server", MessageType.ALREADYTAKENGAMECODEMESSAGE ));
                            sendMessage(new Message("server", MessageType.CHOOSE_GAME_MODE ));
                        }
                        else{
                            lobbyController.addPrivateGameCode(chosenGameModeMessage.getCode(), this, chosenGameModeMessage.getNumOfPlayer());
                        }
                    }
                }

            }
            case CHOSEN_CARDS_REPLY -> {
                ChosenCardsMessage chosenCards = (ChosenCardsMessage) message;
                gameController.removeCards(chosenCards.getCoordinates());
                sendMessage(new Message("server", MessageType.CHOOSE_COLUMN_REQUEST));
            }
            case CHOSEN_COLUMN_REPLY -> {
                ChosenColumnMessage chosenColumn = (ChosenColumnMessage) message;
                gameController.insertInBookshelf(chosenColumn.getColumn());
                gameController.notifyNextPlayer();
            }



        }
    }
    public void sendMessage (Message message) {
        try{
            if(!rmi)
                output.writeObject(message);
            else
                RMIMessages.add(message);
        } catch(IOException IOe) {
            IOe.printStackTrace();
            closeEverything();
            closeEverything();
        }
    }

    private void disconnect () {
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

    private void closeEverything() {
        System.out.println("closeEverything");
        try {
            if (socket != null) {
                socket.close();
                socket = null;
            }

            if (input != null) {
                input.close();
            }

            if (output != null) {
                output.close();
            }
        } catch (IOException IOe) {
            IOe.printStackTrace();
            System.out.println("exception in closeEverything");
        }
    }
    public void setGameController(GameController gameController){
        this.gameController = gameController;
    }
    public Message popMessageRMI(){
        return RMIMessages.remove();
    }

    public boolean rmiMessagesEmpty(){
        return RMIMessages.size()==0;
    }

}
