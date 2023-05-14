package org.polimi.server.controller;

import org.polimi.messages.Message;
import org.polimi.messages.MessageType;
import org.polimi.messages.NewPlayerJoinedMessage;
import org.polimi.messages.StartGameMessage;
import org.polimi.server.ClientHandler;

import java.util.ArrayList;

public class LobbyController {



    //to synchronize and add update message
    private ArrayList<ClientHandler> publicListOf2;
    private ArrayList<ClientHandler> publicListOf3;
    private ArrayList<ClientHandler> publicListOf4;

    GameCodeIssuer gameCodeIssuer;
    UsernameIssuer usernameIssuer;

    public LobbyController(GameCodeIssuer gameCodeIssuer, UsernameIssuer usernameIssuer){
        this.gameCodeIssuer = gameCodeIssuer;
        this.usernameIssuer = usernameIssuer;
        this.publicListOf2 = new ArrayList<>(2);
        this.publicListOf3 = new ArrayList<>(3);
        this.publicListOf4 = new ArrayList<>(4);
    }

    public synchronized void insertPlayer(ClientHandler clientHandler, int gameMode){
        switch(gameMode){
            case 2-> {
                publicListOf2.add(clientHandler);
                if(publicListOf2.size()==2){
                    this.createGame(2);
                }
                else {
                    clientHandler.sendMessage(new Message("server", MessageType.WAITING_IN_LOBBY));
                }
            }
            case 3-> {
                publicListOf3.add(clientHandler);
                if(publicListOf3.size()==3){
                    this.createGame(3);
                }
                else{
                    clientHandler.sendMessage(new Message("server", MessageType.WAITING_IN_LOBBY));
                }
            }
            case 4-> {
                publicListOf4.add(clientHandler);
                if(publicListOf3.size()==4){
                    this.createGame(4);
                }
                else{
                    clientHandler.sendMessage(new Message("server", MessageType.WAITING_IN_LOBBY));
                }
            }
        }
    }

    private void createGame(int gameMode){
        switch (gameMode) {
            case 2-> {
                GameController gameController = new GameController(publicListOf2);
                int code = gameCodeIssuer.associateCodeTo(gameController);
                publicListOf2.forEach((clientHandler -> this.usernameIssuer.mapUsernameToGameCode(clientHandler.getUsername(), code)));
                publicListOf2.forEach(clientHandler -> clientHandler.setGameController(gameController));
                publicListOf2.clear();
            }
            case 3-> {
                GameController gameController = new GameController(publicListOf3);
                int code = gameCodeIssuer.associateCodeTo(gameController);
                publicListOf3.forEach((clientHandler -> this.usernameIssuer.mapUsernameToGameCode(clientHandler.getUsername(), code)));
                publicListOf3.forEach(clientHandler -> clientHandler.setGameController(gameController));
                publicListOf3.clear();
            }
            case 4-> {
                GameController gameController = new GameController(publicListOf4);
                int code = gameCodeIssuer.associateCodeTo(gameController);
                publicListOf4.forEach((clientHandler -> this.usernameIssuer.mapUsernameToGameCode(clientHandler.getUsername(), code)));
                publicListOf4.forEach(clientHandler -> clientHandler.setGameController(gameController));
                publicListOf4.clear();
            }
        }
    }

    public void insertPlayerInRandomTwoPlayerGame(ClientHandler clientHandler) {
        synchronized (publicListOf2) {
            publicListOf2.add(clientHandler);
            if(publicListOf2.size()==2){
                this.createGame(2);
            }
        }
    }

    public void insertPlayerInRandomThreePlayerGame(ClientHandler clientHandler) {
        synchronized (publicListOf3) {
            publicListOf3.add(clientHandler);
            if(publicListOf3.size()==3){
                this.createGame(3);
            }
        }
    }

    public void insertPlayerInRandomFourPlayerGame(ClientHandler clientHandler) {
        synchronized (publicListOf4) {
            publicListOf4.add(clientHandler);
            if(publicListOf4.size()==4){
                this.createGame(4);
            }
        }
    }
}
