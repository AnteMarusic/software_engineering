package org.polimi.servernetwork.controller;

import org.polimi.messages.*;
import org.polimi.servernetwork.model.Game;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketClientHandler extends ClientHandler implements Runnable{
    private Socket socket;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    public SocketClientHandler(Socket socket, UsernameIssuer usernameIssuer, GameCodeIssuer gameCodeIssuer, LobbyController lobbyController) throws IOException {
        super(usernameIssuer, gameCodeIssuer, lobbyController);
        this.socket = socket;
        input = new ObjectInputStream(socket.getInputStream());
        output = new ObjectOutputStream(socket.getOutputStream());
    }
    @Override
    public void run() {
        Message messageFromClient = null;
        while (socket != null && socket.isConnected()) {
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
                    usernameIssuer.setConnect(this.getUsername());
                    gameController.reconnect(this);
                }

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
    protected void closeEverything() {
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
    public void sendMessage (Message message) {
        try{
            output.writeObject(message);
        } catch(IOException IOe) {
            IOe.printStackTrace();
            closeEverything();
        }
    }
}
