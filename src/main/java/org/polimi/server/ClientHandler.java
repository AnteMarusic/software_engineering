package org.polimi.server;

import org.polimi.messages.*;
import org.polimi.server.controller.GameController;
import org.polimi.server.controller.OldGameController;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler implements Runnable{
    private Socket socket;
    private final ObjectInputStream input;
    private final ObjectOutputStream output;
    private String name;
    private UsernameIssuer usernameIssuer;
    private GameCodeIssuer gameCodeIssuer;
    private LobbyController lobbyController;
    private GameController gameController;

    public ClientHandler(Socket socket) {
        try {
            this.socket = socket;
            input = new ObjectInputStream(socket.getInputStream());
            output = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setUsername(String name){
        this.name = name;
    }

    public String getUsername(){
        return this.name;
    }

    @Override
    public void run() {
        Message messageFromClient;
        while (socket != null && socket.isConnected()) {
            try {
                messageFromClient = (Message) input.readObject();

                if(messageFromClient != null){
                    System.out.println(messageFromClient);
                    switch (messageFromClient.getMessageType()){
                        case USERNAME -> {
                            InternalComunication internalComunication = usernameIssuer.handleMessage(messageFromClient.getUsername());
                            if(internalComunication == InternalComunication.OK) {
                                sendMessage(new ChooseGameModeMessage("server", GameMode.DEFAULT ));
                            }
                            if(internalComunication == InternalComunication.ALREADY_TAKEN_USERNAME) {
                                sendMessage(new ErrorMessage("server", ErrorType.ALREADY_TAKEN_USERNAME));
                            }
                            if(internalComunication == InternalComunication.RECONNECTION){
                                int gameId = usernameIssuer.getGameID(messageFromClient.getUsername());
                                GameController gameController = gameCodeIssuer.getGameController(gameId);
                                gameController.reconnect(this);
                            }
                        }
                        case CHOOSE_GAME_MODE -> {
                            ChooseGameModeMessage chooseGameModeMessage = (ChooseGameModeMessage) messageFromClient;
                            switch (chooseGameModeMessage.getGameMode()) {
                                case JOIN_RANDOM_GAME_2_PLAYER -> {
                                    lobbyController.insertPlayerInRandomTwoPlayerGame(this);
                                }
                                case JOIN_RANDOM_GAME_3_PLAYER -> {
                                    lobbyController.insertPlayerInRandomThreePlayerGame(this);
                                }
                                case JOIN_RANDOM_GAME_4_PLAYER -> {
                                    lobbyController.insertPlayerInRandomFourPlayerGame(this);
                                }
                            }

                        }
                        case CHOSEN_CARDS_REPLY -> {
                            ChosenCardsMessage chosenCards = (ChosenCardsMessage) messageFromClient;
                            gameController.removeCards(chosenCards.getCards());
                            sendMessage(new Message("server", MessageType.CHOOSE_COLUMN_REQUEST));
                        }
                        case CHOSEN_COLUMN_REPLY -> {
                            ChosenColumnReply chosenColumn = (ChosenColumnReply) messageFromClient;
                            gameController.insertInBookshelf(chosenColumn.getColumn());
                            gameController.notifyNextPlayer();
                        }



                    }
                }
                else {
                    closeEverything();
                }
            } catch (IOException e) {
                closeEverything();
                System.out.println("exception IOe in ClientHandler run");
            } catch (ClassNotFoundException e) {
                closeEverything();
                System.out.println("exception class not found in ClientHandler run");
            }
        }
        if (socket != null) {
            closeEverything();
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
