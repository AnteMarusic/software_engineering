package org.polimi.server.controller;

import org.polimi.messages.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler implements Runnable{
    private Socket socket;
    private final ObjectInputStream input;
    private final ObjectOutputStream output;
    private String username;
    private UsernameIssuer usernameIssuer;
    private GameCodeIssuer gameCodeIssuer;
    private LobbyController lobbyController;
    private GameController gameController;

    public ClientHandler(Socket socket, UsernameIssuer usernameIssuer, GameCodeIssuer gameCodeIssuer, LobbyController lobbyController) {
        try {
            this.usernameIssuer = usernameIssuer;
            this.gameCodeIssuer = gameCodeIssuer;
            this.lobbyController = lobbyController;
            this.socket = socket;
            input = new ObjectInputStream(socket.getInputStream());
            output = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
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
        Message messageFromClient;
        while (socket != null && socket.isConnected()) {
            try {messageFromClient = (Message) input.readObject();
                // posso scartare tutti i messaggi di un client che è gia connesso alla partita se non è il suo turno

                if(messageFromClient != null){
                    System.out.println(messageFromClient);
                    switch (messageFromClient.getMessageType()){
                        case USERNAME -> {
                            InternalComunication internalComunication = usernameIssuer.handleMessage(messageFromClient.getUsername());
                            if(internalComunication == InternalComunication.OK) {
                                this.username = messageFromClient.getUsername();
                                sendMessage(new Message("server", MessageType.CHOOSE_GAME_MODE ));
                            }
                            if(internalComunication == InternalComunication.ALREADY_TAKEN_USERNAME) {
                                sendMessage(new ErrorMessage("server", ErrorType.ALREADY_TAKEN_USERNAME));
                            }
                            //to test
                            if(internalComunication == InternalComunication.RECONNECTION){
                                this.username = messageFromClient.getUsername();
                                int gameId = usernameIssuer.getGameID(messageFromClient.getUsername());
                                GameController gameController = gameCodeIssuer.getGameController(gameId);
                                usernameIssuer.setConnect(this.getUsername());
                                gameController.reconnect(this);
                            }
                        }
                        case CHOOSE_GAME_MODE -> {
                            ChosenGameModeMessage chosenGameModeMessage = (ChosenGameModeMessage) messageFromClient;
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
                                //to do: private game
                            }

                        }
                        case CHOSEN_CARDS_REPLY -> {
                            ChosenCardsMessage chosenCards = (ChosenCardsMessage) messageFromClient;
                            gameController.removeCards(chosenCards.getCoordinates());
                            sendMessage(new Message("server", MessageType.CHOOSE_COLUMN_REQUEST));
                        }
                        case CHOSEN_COLUMN_REPLY -> {
                            ChosenColumnMessage chosenColumn = (ChosenColumnMessage) messageFromClient;
                            gameController.insertInBookshelf(chosenColumn.getColumn());
                            gameController.notifyNextPlayer();
                        }



                    }
                }
                else {
                    //message == null. we suppose that this happens because you disconnected
                    System.out.println("received a null message from client " + username);
                    disconnect();
                }
            } catch (IOException e) {
                System.out.println("client " + username + "disconnected");
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


    public void sendMessage (Message message) {
        try{
            output.writeObject(message);
        } catch(IOException IOe) {
            IOe.printStackTrace();
            closeEverything();
            closeEverything();
        }
    }

    private void disconnect () {
        if (lobbyController == null) {
            throw new NullPointerException();
        }

        //if game controller is null you are either in a lobby or waiting to get in one
        //so, you should disconnect from it
        if (gameController == null) {
            lobbyController.disconnect(this);
        }
        //if you are in a game you have to be disconnected from it
        if (gameController != null) {
            gameController.disconnection(this);
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

}
